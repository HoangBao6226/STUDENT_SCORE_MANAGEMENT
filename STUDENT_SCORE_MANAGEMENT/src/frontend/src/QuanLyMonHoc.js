import React, { useEffect, useState } from 'react';
import axios from 'axios';

const QuanLyMonHoc = () => {
    const [monHocList, setMonHocList] = useState(window.initialData.danhSachMonHoc || []);

    useEffect(() => {
        axios.get('http://localhost:8080/api/admin/monhoc')
            .then(res => {
                console.log("Dữ liệu từ backend:", res.data);
                setMonHocList(res.data);
            })
            .catch(err => {
                console.error("Lỗi khi gọi API:", err);
            });
    }, []);

    const handleDelete = async (id) => {
        if (window.confirm('Bạn có chắc chắn muốn xóa môn học này?')) {
            try {
                const response = await axios.delete(`http://localhost:8080/api/admin/monhoc/${id}`);
                if (response.data) {
                    setMonHocList(monHocList.filter(monHoc => monHoc.maMH !== id));
                    alert('Xóa môn học thành công!');
                }
            } catch (err) {
                console.error("Lỗi khi xóa môn học:", err);
                alert('Xóa môn học thất bại!');
            }
        }
    };

    return (
        <table className="table table-bordered" style={{ tableLayout: 'fixed', width: '100%' }}>
            <thead>
            <tr>
                <th style={{ width: '10%' }}>Mã MH</th>
                <th style={{ width: '25%' }}>Tên Môn Học</th>
                <th style={{ width: '15%' }}>Số Tín Chỉ</th>
                <th style={{ width: '25%' }}>Mã Giảng Viên</th>
                <th style={{ width: '25%' }}>Tùy Chọn</th>
            </tr>
            </thead>
            <tbody>
            {monHocList.length > 0 ? (
                monHocList.map((monHoc) => (
                    <tr key={monHoc.maMH}>
                        <td>{monHoc.maMH}</td>
                        <td>{monHoc.tenMH}</td>
                        <td>{monHoc.soTinChi}</td>
                        <td>{monHoc.maGV !== null ? monHoc.maGV : 'N/A'}</td>
                        <td>
                            <a href={`/admin/editMonHoc/${monHoc.maMH}`} className="btn btn-success btn-sm">Sửa</a>
                            <button
                                className="btn btn-danger btn-sm"
                                onClick={() => handleDelete(monHoc.maMH)}
                            >
                                Xóa
                            </button>
                        </td>
                    </tr>
                ))
            ) : (
                <tr>
                    <td colSpan="5" style={{ textAlign: 'center' }}>Không có môn học nào</td>
                </tr>
            )}
            </tbody>
        </table>
    );
};

export default QuanLyMonHoc;