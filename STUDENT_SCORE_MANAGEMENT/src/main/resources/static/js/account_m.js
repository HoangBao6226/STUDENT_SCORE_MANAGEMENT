// Guard to prevent multiple executions
if (!window.adminTaiKhoanInitialized) {
    window.adminTaiKhoanInitialized = true;

    document.addEventListener('DOMContentLoaded', function () {
        var isSubmitting = false; // Biến theo dõi trạng thái gửi
        console.log('DOMContentLoaded triggered for admin-taikhoan.js');
        const accountForm = document.getElementById("account-form");
        const editForm = document.getElementById("editForm"); // Sửa thành editForm để khớp với HTML
        const submitButton = accountForm ? accountForm.querySelector('button[type="submit"]') : null;
        const editSubmitButton = editForm ? editForm.querySelector('.btn-primary') : null; // Nút "Lưu Thay Đổi"

        // Hàm xử lý form thêm tài khoản
        function addTaiKhoan(event) {
            event.preventDefault(); // Ngăn hành vi mặc định của form

            if (isSubmitting || !submitButton) return; // Nếu đang gửi hoặc không có nút, bỏ qua

            // Vô hiệu hóa nút submit để tránh gửi nhiều yêu cầu
            submitButton.disabled = true;

            const formData = new FormData(accountForm);
            const data = Object.fromEntries(formData);

            isSubmitting = true;
            fetch("/admin/addTaiKhoan", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(err => { throw new Error(err.message); });
                    }
                    return response.json();
                })
                .then(data => {
                    const message = data.message || "Tạo tài khoản thành công!";
                    const alertDiv = document.createElement('div');
                    alertDiv.className = `alert alert-${message.includes("thành công") ? 'success' : 'danger'} alert-dismissible`;
                    alertDiv.innerHTML = `
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <p>${message}</p>
                    `;
                    document.querySelector('.content').insertBefore(alertDiv, document.querySelector('.box'));

                    if (message.includes("thành công")) {
                        accountForm.reset();
                        setTimeout(() => {
                            window.location.href = "/admin/index/listTaiKhoan";
                        }, 1000); // Chuyển hướng sau 1 giây
                    }
                })
                .catch(error => {
                    const errorDiv = document.createElement('div');
                    errorDiv.className = 'alert alert-danger alert-dismissible';
                    errorDiv.innerHTML = `
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <p>${error.message || "Có lỗi xảy ra khi tạo tài khoản!"}</p>
                    `;
                    document.querySelector('.content').insertBefore(errorDiv, document.querySelector('.box'));
                })
                .finally(() => {
                    isSubmitting = false; // Đánh dấu đã hoàn thành
                    submitButton.disabled = false; // Kích hoạt lại nút submit
                });
        }

        // Hàm xử lý sửa tài khoản (tích hợp từ yêu cầu của bạn)
        window.submitEditForm = function () {
            if (isSubmitting || !editSubmitButton) return; // Nếu đang gửi hoặc không có nút, bỏ qua

            // Vô hiệu hóa nút "Lưu Thay Đổi"
            editSubmitButton.disabled = true;

            const maTK = document.getElementById("maTK").value;
            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;
            const account_type = document.getElementById("role").value;

            isSubmitting = true;
            fetch(`/admin/editTaiKhoan/${maTK}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    username: username,
                    password: password,
                    account_type: account_type
                })
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(err => { throw new Error(err.message); });
                    }
                    return response.json();
                })
                .then(data => {
                    const message = data.message || "Cập nhật tài khoản thành công!";
                    const alertDiv = document.createElement('div');
                    alertDiv.className = `alert alert-${message.includes("thành công") ? 'success' : 'danger'} alert-dismissible`;
                    alertDiv.innerHTML = `
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <p>${message}</p>
                    `;
                    document.querySelector('.content').insertBefore(alertDiv, document.querySelector('.box'));

                    if (message.includes("thành công")) {
                        setTimeout(() => {
                            window.location.href = "/admin/index/listTaiKhoan";
                        }, 1000); // Chuyển hướng sau 1 giây
                    }
                })
                .catch(error => {
                    const errorDiv = document.createElement('div');
                    errorDiv.className = 'alert alert-danger alert-dismissible';
                    errorDiv.innerHTML = `
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <p>${error.message || "Có lỗi xảy ra khi cập nhật tài khoản!"}</p>
                    `;
                    document.querySelector('.content').insertBefore(errorDiv, document.querySelector('.box'));
                })
                .finally(() => {
                    isSubmitting = false; // Đánh dấu đã hoàn thành
                    editSubmitButton.disabled = false; // Kích hoạt lại nút
                });
        };

        // Hàm xóa tài khoản
        function deleteTaiKhoan(id) {
            if (isSubmitting) return;

            if (!confirm("Bạn có chắc muốn xóa tài khoản này?")) {
                return;
            }

            isSubmitting = true;
            fetch(`/admin/deleteTaiKhoan/${id}`, {
                method: "DELETE",
                headers: { "Content-Type": "application/json" }
            })
                .then(response => {
                    if (!response.ok) {
                        return response.text().then(err => { throw new Error(err); });
                    }
                    return response.text();
                })
                .then(message => {
                    const alertDiv = document.createElement('div');
                    alertDiv.className = `alert alert-${message.includes("thành công") ? 'success' : 'danger'} alert-dismissible`;
                    alertDiv.innerHTML = `
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <p>${message}</p>
                ${message.includes("điểm môn học") ?
                        '<p><a href="/admin/index/listDiem?maSV=' + id + '">Xem điểm sinh viên</a></p>' : ''}
                ${message.includes("môn học đang giảng dạy") ?
                        '<p><a href="/admin/index/listMonHoc?maGV=' + id + '">Xem môn học giảng dạy</a></p>' : ''}
            `;
                    document.querySelector('.content').insertBefore(alertDiv, document.querySelector('.box'));

                    if (message.includes("thành công")) {
                        setTimeout(() => {
                            location.reload();
                        }, 1000);
                    }
                })
                .catch(error => {
                    const errorDiv = document.createElement('div');
                    errorDiv.className = 'alert alert-danger alert-dismissible';
                    errorDiv.innerHTML = `
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <p>${error.message || "Có lỗi xảy ra khi xóa tài khoản!"}</p>
            `;
                    document.querySelector('.content').insertBefore(errorDiv, document.querySelector('.box'));
                })
                .finally(() => {
                    isSubmitting = false;
                });
        }

        // Xử lý form thêm tài khoản
        if (accountForm && submitButton) {
            accountForm.addEventListener("submit", addTaiKhoan);
        }

        // Gắn hàm xóa tài khoản vào window để có thể gọi từ HTML
        window.deleteAccount = deleteTaiKhoan;
    });
}