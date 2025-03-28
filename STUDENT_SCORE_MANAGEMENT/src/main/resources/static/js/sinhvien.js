document.addEventListener('DOMContentLoaded', function () {
    // Lấy maGV và maMH từ các biến được truyền vào từ Thymeleaf
    const maGV = document.getElementById('maGV').value;
    const maMH = document.getElementById('maMH').value;

    // Hàm lấy danh sách sinh viên
    function fetchSinhVienList() {
        fetch(`/giangvien/monhoc/${maGV}/${maMH}/sinhvien-list`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Không thể lấy danh sách sinh viên');
                }
                return response.json();
            })
            .then(data => {
                const tbody = document.getElementById('sinhVienTableBody');
                tbody.innerHTML = ''; // Xóa nội dung hiện tại của bảng

                if (data.length === 0) {
                    const row = document.createElement('tr');
                    row.innerHTML = '<td colspan="5" style="text-align: center;">Không có sinh viên nào</td>';
                    tbody.appendChild(row);
                } else {
                    data.forEach(sinhVien => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                        <td>${sinhVien.maSV}</td>
                        <td>${sinhVien.tenSV}</td>
                        <td>${sinhVien.tenGV}</td>
                        <td>${sinhVien.maMH}</td>
                        <td>
                            <a href="/giangvien/diem/${maGV}/${maMH}/${sinhVien.maSV}" class="btn btn-info btn-sm">Xem điểm</a>
                            <form action="/giangvien/monhoc/${maGV}/${maMH}/delete-sinhvien/${sinhVien.maSV}" method="post" style="display:inline;">
                                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc chắn muốn xóa sinh viên này?')">Xóa</button>
                            </form>
                        </td>
                    `;
                        tbody.appendChild(row);
                    });
                }
            })
            .catch(error => {
                console.error('Lỗi:', error);
                const tbody = document.getElementById('sinhVienTableBody');
                tbody.innerHTML = '<tr><td colspan="5" style="text-align: center;">Lỗi khi tải danh sách sinh viên</td></tr>';
            });
    }

    // Gọi lần đầu khi trang tải
    fetchSinhVienList();

    // Xử lý form thêm sinh viên
    document.getElementById('addSinhVienForm').addEventListener('submit', function (event) {
        event.preventDefault(); // Ngăn form submit mặc định

        const maSV = document.getElementById('maSV').value;
        const tenSV = document.getElementById('tenSV').value;

        const sinhVienData = {
            maSV: parseInt(maSV),
            tenSV: tenSV
        };

        fetch(`/giangvien/monhoc/${maGV}/${maMH}/add-sinhvien`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(sinhVienData)
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => { throw new Error(err.message); });
                }
                return response.json();
            })
            .then(data => {
                // Hiển thị thông báo thành công
                const successDiv = document.createElement('div');
                successDiv.className = 'alert alert-success alert-dismissible';
                successDiv.innerHTML = `
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <p>${data.message}</p>
            `;
                document.querySelector('.content').insertBefore(successDiv, document.querySelector('.box'));

                // Reset form
                document.getElementById('addSinhVienForm').reset();

                // Refresh danh sách sinh viên
                fetchSinhVienList();
            })
            .catch(error => {
                // Hiển thị thông báo lỗi
                const errorDiv = document.createElement('div');
                errorDiv.className = 'alert alert-danger alert-dismissible';
                errorDiv.innerHTML = `
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <p>${error.message}</p>
            `;
                document.querySelector('.content').insertBefore(errorDiv, document.querySelector('.box'));
            });
    });
});