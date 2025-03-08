// Hàm hiển thị section tương ứng
function showSection(sectionId) {
    // Ẩn tất cả các section
    document.querySelectorAll('.content-section').forEach(section => {
        section.style.display = 'none';
    });

    // Hiển thị section được chọn
    document.getElementById(sectionId).style.display = 'block';
}

// Xử lý form cấp tài khoản
document.getElementById('account-form').addEventListener('submit', function(event) {
    event.preventDefault();
    const accountType = document.getElementById('account-type').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Xử lý dữ liệu (ví dụ: gửi lên server)
    console.log(`Tạo tài khoản: ${accountType}, Tên đăng nhập: ${username}, Mật khẩu: ${password}`);
    alert('Tài khoản đã được tạo thành công!');
});

// Xử lý form quản lý điểm
document.getElementById('score-form').addEventListener('submit', function(event) {
    event.preventDefault();
    const studentId = document.getElementById('student-id').value;
    const subjectId = document.getElementById('subject-id').value;
    const score = document.getElementById('score').value;

    // Xử lý dữ liệu (ví dụ: gửi lên server)
    console.log(`Cập nhật điểm: Mã sinh viên: ${studentId}, Mã môn học: ${subjectId}, Điểm: ${score}`);
    alert('Điểm đã được cập nhật thành công!');
});

// Xử lý form quản lý môn học
document.getElementById('subject-form').addEventListener('submit', function(event) {
    event.preventDefault();
    const subjectName = document.getElementById('subject-name').value;
    const subjectCode = document.getElementById('subject-code').value;

    // Xử lý dữ liệu (ví dụ: gửi lên server)
    console.log(`Thêm môn học: ${subjectName}, Mã môn học: ${subjectCode}`);
    alert('Môn học đã được thêm thành công!');
});