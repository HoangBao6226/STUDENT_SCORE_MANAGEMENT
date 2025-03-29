// Guard to prevent multiple executions
if (!window.dangKyMonHocInitialized) {
    window.dangKyMonHocInitialized = true;

    // Thêm một biến để theo dõi trạng thái đang gửi
    let isSubmitting = false;

    document.addEventListener('DOMContentLoaded', function () {
        console.log('DOMContentLoaded triggered');
        const maSV = document.getElementById('maSV').value;

        // Hàm hiển thị thông báo
        function showAlert(message, type) {
            const alertDiv = document.createElement('div');
            alertDiv.className = `alert alert-${type} alert-dismissible`;
            alertDiv.innerHTML = `
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <p>${message}</p>
            `;
            document.querySelector('.content').insertBefore(alertDiv, document.querySelector('.box'));
            // Tự động ẩn sau 5 giây
            setTimeout(() => alertDiv.remove(), 5000);
        }

        // Hàm lấy danh sách môn học có sẵn
        function fetchMonHocList() {
            console.log('Fetching available subjects from /api/monhoc');
            fetch('/api/monhoc', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then(data => {
                    populateMonHocTable(data, true); // Thêm hiệu ứng động
                })
                .catch(error => {
                    console.error('Lỗi:', error);
                    const tbody = document.getElementById('monHocTableBody');
                    tbody.innerHTML = '<tr><td colspan="5" style="text-align: center;">Lỗi khi tải danh sách môn học</td></tr>';
                });
        }

        // Hàm tìm kiếm môn học theo từ khóa
        function searchMonHoc(keyword) {
            console.log('Searching subjects with keyword:', keyword);
            fetch(`/monhoc/search?keyword=${encodeURIComponent(keyword)}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Không tìm thấy môn học');
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.monHocList && data.monHocList.length > 0) {
                        populateMonHocTable(data.monHocList, true); // Thêm hiệu ứng động
                    } else {
                        populateMonHocTable([], true);
                    }
                })
                .catch(error => {
                    console.error('Lỗi tìm kiếm:', error);
                    populateMonHocTable([], true);
                });
        }

        // Hàm hiển thị danh sách môn học vào bảng với hiệu ứng động
        function populateMonHocTable(monHocList, withAnimation = false) {
            const tbody = document.getElementById('monHocTableBody');
            tbody.innerHTML = ''; // Xóa nội dung hiện tại của bảng

            if (monHocList.length === 0) {
                tbody.innerHTML = '<tr><td colspan="5" style="text-align: center;">Không có môn học nào</td></tr>';
            } else {
                monHocList.forEach((monHoc, index) => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td><input type="checkbox" class="monHocCheckbox" value="${monHoc.maMH}"></td>
                        <td>${monHoc.maMH}</td>
                        <td>${monHoc.tenMH}</td>
                        <td>${monHoc.tenGV || 'Chưa có'}</td>
                        <td>${monHoc.soTinChi}</td>
                    `;
                    if (withAnimation) {
                        row.style.opacity = '0';
                        row.style.transform = 'translateY(20px)';
                        setTimeout(() => {
                            row.style.transition = 'opacity 0.3s ease, transform 0.3s ease';
                            row.style.opacity = '1';
                            row.style.transform = 'translateY(0)';
                        }, index * 50); // Hiệu ứng xuất hiện lần lượt
                    }
                    tbody.appendChild(row);
                });
            }
        }

        // Hàm lấy danh sách môn học đã đăng ký
        function fetchDiemList() {
            console.log('Fetching registered subjects for maSV:', maSV);
            fetch(`/sinhvien/${maSV}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(response.statusText);
                    }
                    return response.json();
                })
                .then(data => {
                    const tbody = document.getElementById('diemTableBody');
                    tbody.innerHTML = ''; // Xóa nội dung hiện tại của bảng

                    if (data.length === 0) {
                        tbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">Chưa đăng ký môn học nào</td></tr>';
                    } else {
                        data.forEach(diem => {
                            const row = document.createElement('tr');
                            row.innerHTML = `
                                <td><input type="checkbox" class="diemCheckbox" value="${diem.maMH}"></td>
                                <td>${diem.maMH}</td>
                                <td>${diem.tenMH}</td>
                                <td>${diem.tenGV || 'Chưa có'}</td>
                                <td>${diem.soTinChi}</td>
                                <td>${diem.diem !== null ? diem.diem : 'Chưa có điểm'}</td>
                            `;
                            tbody.appendChild(row);
                        });
                    }
                })
                .catch(error => {
                    console.error('Lỗi:', error);
                    const tbody = document.getElementById('diemTableBody');
                    tbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">Lỗi khi tải danh sách môn học đã đăng ký</td></tr>';
                });
        }

        // Select All cho danh sách môn học có sẵn
        document.getElementById('selectAllAvailable').addEventListener('change', function () {
            const checkboxes = document.querySelectorAll('.monHocCheckbox');
            checkboxes.forEach(checkbox => {
                checkbox.checked = this.checked;
            });
        });

        // Select All cho danh sách môn học đã đăng ký
        document.getElementById('selectAllRegistered').addEventListener('change', function () {
            const checkboxes = document.querySelectorAll('.diemCheckbox');
            checkboxes.forEach(checkbox => {
                checkbox.checked = this.checked;
            });
        });

        // Debounce để hạn chế số lần gọi API khi nhập nhanh
        function debounce(func, wait) {
            let timeout;
            return function (...args) {
                clearTimeout(timeout);
                timeout = setTimeout(() => func.apply(this, args), wait);
            };
        }

        // Xử lý sự kiện nhập ký tự vào ô tìm kiếm
        document.getElementById('searchKeyword').addEventListener('input', debounce(function () {
            const keyword = this.value.trim();
            if (keyword) {
                searchMonHoc(keyword);
            } else {
                fetchMonHocList(); // Nếu ô trống, tải lại danh sách đầy đủ
            }
        }, 300)); // Trễ 300ms

        // Hàm gọi API đăng ký môn học
        function dangKyMonHoc() {
            if (isSubmitting) return; // Nếu đang gửi request, bỏ qua

            const selectedMonHoc = [];
            document.querySelectorAll('.monHocCheckbox:checked').forEach(checkbox => {
                selectedMonHoc.push(parseInt(checkbox.value));
            });

            if (selectedMonHoc.length === 0) {
                showAlert('Vui lòng chọn ít nhất một môn học để đăng ký!', 'warning');
                return;
            }

            const dangKyData = {
                maSV: parseInt(maSV),
                maMHList: selectedMonHoc
            };

            isSubmitting = true;
            fetch('/sinhvien/dangky', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dangKyData)
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(err => { throw new Error(err.message); });
                    }
                    return response.json();
                })
                .then(data => {
                    showAlert(data.message, 'success');
                    fetchDiemList(); // Cập nhật danh sách đã đăng ký
                    fetchMonHocList(); // Cập nhật danh sách có sẵn
                })
                .catch(error => {
                    showAlert(error.message, 'danger');
                })
                .finally(() => {
                    isSubmitting = false; // Đánh dấu đã hoàn thành
                });
        }

        // Hàm gọi API hủy đăng ký môn học
        function xoaMonHoc() {
            if (isSubmitting) return; // Nếu đang gửi request, bỏ qua

            const selectedDiem = [];
            document.querySelectorAll('.diemCheckbox:checked').forEach(checkbox => {
                selectedDiem.push(parseInt(checkbox.value));
            });

            if (selectedDiem.length === 0) {
                showAlert('Vui lòng chọn ít nhất một môn học để hủy đăng ký!', 'warning');
                return;
            }

            const xoaData = {
                maSV: parseInt(maSV),
                maMHList: selectedDiem
            };

            isSubmitting = true;
            fetch('/sinhvien/xoa', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(xoaData)
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(err => { throw new Error(err.message); });
                    }
                    return response.json();
                })
                .then(data => {
                    let message = data.message;
                    if (data.daXoa && data.daXoa.length > 0) {
                        message += ` Đã xóa: ${data.daXoa.join(', ')}.`;
                    }
                    if (data.khongTheXoa && data.khongTheXoa.length > 0) {
                        message += ` Không thể xóa (đã có điểm): ${data.khongTheXoa.join(', ')}.`;
                    }
                    showAlert(message, 'success');
                    fetchDiemList(); // Cập nhật danh sách đã đăng ký
                    fetchMonHocList(); // Cập nhật danh sách có sẵn
                })
                .catch(error => {
                    showAlert(error.message, 'danger');
                })
                .finally(() => {
                    isSubmitting = false; // Đánh dấu đã hoàn thành
                });
        }

        // Gắn sự kiện cho nút "Đăng Ký"
        document.getElementById('dangKyButton').addEventListener('click', dangKyMonHoc);

        // Gắn sự kiện cho nút "Hủy Đăng Ký"
        document.getElementById('xoaButton').addEventListener('click', xoaMonHoc);

        // Gọi lần đầu khi trang tải
        fetchMonHocList();
        fetchDiemList();
    });
}