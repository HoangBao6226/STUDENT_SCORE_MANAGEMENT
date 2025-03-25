import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import QuanLyMonHoc from './QuanLyMonHoc';
import QuanLyDiem from './QuanLyDiem';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/admin/monhoc" element={<QuanLyMonHoc />} />
                <Route path="/admin/diem" element={<QuanLyDiem />} />
            </Routes>
        </Router>
    );
}

export default App;