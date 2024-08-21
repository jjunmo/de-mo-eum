import styles from "../styles.module.css";
import Link from "next/link";

const Home = () => {
    return (
        <div className={styles.hello}>
            <h1>Home</h1>
            <p>Hello World! This is the Home page</p>
            <p>
                Visit the <Link href="about">About</Link> page.
            </p>
            <p>
                Visit the <Link href="test1">test1</Link> page.
            </p>
            <p>
                Visit the <Link href="test1/test3">test1/test3</Link> page.
            </p>
            <p>
                Visit the <Link href="test2">/test2</Link> page.
            </p>
            <p>
                Visit the <Link href="login">/login</Link> page.
            </p>
        </div>
    );
};

export default Home;