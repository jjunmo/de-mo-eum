import LoginForm from './loginForm';
import styles from './login.module.css';
import Link from "next/link";

const LoginPage = () => {
    return (
        <div className={styles.container}>
            <header className={styles.header}>
            </header>
            <LoginForm/>
            <Link href="../test2/homepage">homepage</Link>
            <Link href="../test2/inquirypage">inquirypage</Link>
            <Link href="../test2/noticepage">noticepage</Link>
            <Link href="/">Home</Link>
        </div>
    );
};

export default LoginPage;