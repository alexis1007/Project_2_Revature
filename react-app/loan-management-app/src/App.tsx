import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Layout } from './Layout';
import { LoginPage } from './auth/LoginPage';
import { RegisterPage } from './auth/RegisterPage';
import { Loans } from './pages/Loans';
import { DashboardPage } from './pages/DashboardPage';
import { UserProfiles } from './pages/Profile';

const App = () => (
  <Router>
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<LoginPage />} />
        <Route path="register" element={<RegisterPage />} />
        <Route path="dashboard" element={<DashboardPage />} />
        <Route path="loans" element={<Loans />} />
        <Route path="profile" element={<UserProfiles />} />
      </Route>
    </Routes>
  </Router>
);

export default App;
