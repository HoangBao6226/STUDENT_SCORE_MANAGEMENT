import React, { useEffect, useState } from 'react';
import axios from 'axios';

const QuanLyDiem = () => {
    const [diemList, setDiemList] = useState(window.initialData.danhSachDiem || []);

    useEffect(() => {
        axios.get('http://localhost:8080/api/admin/diem')
            .then(res => {
                console.log("Dữ liệu từ backend:", res.data);
                setDiemList(res.data);
            })
            .catch(err => {
                console.error("Lỗi khi gọi API:", err);
            });
    }, []);

    const handleDelete = async (id) => {
        if (window.confirm('Bạn có chắc chắn muốn xóa điểm này?')) {
            try {
                const response = await axios.delete(`http://localhost:8080/api/admin/diem/${id}`);
                if (response.data === "Xóa điểm thành công!") {
                    setDiemList(diemList.filter(diem => diem.id !== id));
                    alert('Xóa điểm thành công!');
                }
            } catch (err) {
                console.error("Lỗi khi xóa điểm:", err);
                alert('Xóa điểm thất bại!');
            }
        }
    };

    return (
        <table className="table table-bordered" style={{ tableLayout: 'fixed', width: '100%' }}>
            <thead>
            <tr>
                <th style={{ width: '10%' }}>ID</th>
                <th style={{ width: '15%' }}>Mã Sinh Viên</th>
                <th style={{ width: '15%' }}>Mã Môn Học</th>
                <th style={{ width: '15%' }}>Điểm</th>
                <th style={{ width: '15%' }}>Học Kỳ</th>
                <th style={{ width: '15%' }}>Năm Học</th>
                <th style={{ width: '15%' }}>Tùy Chọn</th>
            </tr>
            </thead>
            <tbody>
            {diemList.length > 0 ? (
                diemList.map((diem) => (
                    <tr key={diem.id}>
                        <td>{diem.id}</td>
                        <td>{diem.maSV}</td>
                        <td>{diem.maMH}</td>
                        <td>{diem.diem}</td>
                        <td>{diem.hocKy}</td>
                        <td>{diem.namHoc}</td>
                        <td>
                            <a href={`/admin/editDiem/${diem.id}`} className="btn btn-success btn-sm">Sửa</a>
                            <button
                                className="btn btn-danger btn-sm"
                                onClick={() => handleDelete(diem.id)}
                            >
                                Xóa
                            </button>
                        </td>
                    </tr>
                ))
            ) : (
                <tr>
                    <td colSpan="7" style={{ textAlign: 'center' }}>Không có điểm nào</td>
                </tr>
            )}
            </tbody>
        </table>
    );
};

export default QuanLyDiem;