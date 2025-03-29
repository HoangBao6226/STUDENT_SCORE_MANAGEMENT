function updateDiem() {
    const maGV = document.getElementById('maGV').value;
    const maMH = document.getElementById('maMH').value;
    const maSV = document.getElementById('maSV').value;
    let diem = document.getElementById('diemInput').value;

    // Validate and process the score
    let processedDiem = null;
    if (diem) {
        // Convert to float and round to 1 decimal place
        processedDiem = parseFloat(diem);

        // Check if the score is within valid range (0-10)
        if (processedDiem < 0 || processedDiem > 10) {
            // Show error message for invalid range
            const errorDiv = document.createElement('div');
            errorDiv.className = 'alert alert-danger alert-dismissible';
            errorDiv.innerHTML = `
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <p>Điểm phải nằm trong khoảng từ 0 đến 10</p>
            `;
            document.querySelector('.content').insertBefore(errorDiv, document.querySelector('.box'));
            return; // Exit the function if validation fails
        }

        // Round to 1 decimal place
        processedDiem = Math.round(processedDiem * 10) / 10;
    }

    const diemData = {
        maSV: parseInt(maSV),
        maMH: parseInt(maMH),
        diem: processedDiem
    };

    fetch(`/giangvien/diem/${maGV}/${maMH}/${maSV}/update`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(diemData)
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
}