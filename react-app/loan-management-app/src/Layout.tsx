import { Outlet } from 'react-router-dom';

export const Layout = () => {
  return (
    <div className="main-container">
      <h1 className="app-title">Loans Management System</h1>
      <Outlet /> {/* This is where your pages will render */}
      <footer className="app-footer">
        © Copyright {new Date().getFullYear()} Loans Management System. All
        rights reserved.
      </footer>
    </div>
  );
};
