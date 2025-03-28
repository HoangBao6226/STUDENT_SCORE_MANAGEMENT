// monHoc.js
document.addEventListener('DOMContentLoaded', function () {
    // Xử lý form thêm môn học
    const addForm = document.getElementById("addForm");
    if (addForm) {
        addForm.addEventListener("submit", function(event) {
            event.preventDefault();

            // Lấy dữ liệu từ form
            const tenMH = document.getElementById("tenMH").value;
            const soTinChi = document.getElementById("soTinChi").value;
            const maGV = document.getElementById("maGV").value;

            // Tạo object dữ liệu để gửi
            const data = {
                tenMH: tenMH,
                soTinChi: parseInt(soTinChi),
                maGV: maGV ? parseInt(maGV) : null // Chuyển thành null nếu maGV rỗng
            };

            // Gửi request POST tới endpoint API
            fetch('/api/monhoc', {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(err => { throw err; });
                    }
                    return response.json();
                })
                .then(json => {
                    alert(json.message || "Thêm môn học thành công!");
                    addForm.reset(); // Reset form sau khi thêm thành công
                    window.location.href = "/admin/monhoc"; // Chuyển hướng về danh sách
                })
                .catch(error => {
                    alert(error.message || "Có lỗi xảy ra khi thêm môn học!");
                    console.error("Lỗi:", error);
                });
        });
    }

    // Xử lý form sửa môn học
    const editForm = document.getElementById("editForm");
    if (editForm) {
        editForm.addEventListener("submit", function(event) {
            event.preventDefault();

            // Lấy dữ liệu từ form
            const maMH = document.getElementById("maMH").value;
            const tenMH = document.getElementById("tenMH").value;
            const soTinChi = document.getElementById("soTinChi").value;
            const maGV = document.getElementById("maGV").value;

            // Tạo object dữ liệu để gửi
            const data = {
                maMH: parseInt(maMH),
                tenMH: tenMH,
                soTinChi: parseInt(soTinChi),
                maGV: maGV ? parseInt(maGV) : null
            };

            // Gửi request PUT tới endpoint API
            fetch(`/api/monhoc/${maMH}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(err => { throw err; });
                    }
                    return response.json();
                })
                .then(json => {
                    alert(json.message || "Cập nhật môn học thành công!");
                    window.location.href = "/admin/monhoc";
                })
                .catch(error => {
                    alert(error.message || "Có lỗi xảy ra khi cập nhật môn học!");
                    console.error("Lỗi:", error);
                });
        });
    }
});