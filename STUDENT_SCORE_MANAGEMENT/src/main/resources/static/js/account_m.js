document.addEventListener('DOMContentLoaded', function () {
    const accountForm = document.getElementById("account-form");
    if (accountForm) {
        accountForm.addEventListener("submit", function(event) {
            event.preventDefault();

            const formData = new FormData(this);
            const data = Object.fromEntries(formData);

            fetch("/admin/addTaiKhoan", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            })
                .then(response => response.json())
                .then(json => {
                    alert(json.message || "Tạo tài khoản thành công!");
                    accountForm.reset();
                })
                .catch(error => {
                    alert("Có lỗi xảy ra!");
                    console.error("Lỗi:", error);
                });
        });
    }
});
function submitEditForm() {
    const maTK = document.getElementById("maTK").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const account_type = document.getElementById("role").value;

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
            if (!response.ok) return response.json().then(err => { throw err; });
            return response.json();
        })
        .then(data => {
            alert(data.message || "Cập nhật thành công!");
            window.location.href = "/admin/index/listTaiKhoan";
        })
        .catch(error => {
            alert(error.message || "Có lỗi xảy ra khi cập nhật.");
        });
}
