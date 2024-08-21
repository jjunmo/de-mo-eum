// @ts-ignore
import styles from '@/app/test2/noticepage/notice.style.css';
import Link from "next/link";

const Home = () => {
    return (
        <div className={styles.screen}>
            <div className="wrap">
                <div className={`${styles.container} ${styles['container-b']}`}>
                    <div className="appbar">
                        <button><img src="/icon-arrow-back.png" alt="뒤로가기 버튼" /></button>
                        <div className="title">교회소식</div>
                    </div>
                    <ul className="list-1">
                        <li>
                            <a href="">
                                <div>
                                    <span>yyyy.mm.dd</span>
                                    <h4>2023년 성탄기념 봉사활동 안내</h4>
                                </div>
                                <img src="/icon-arrow-right.png" alt="버튼 이미지" />
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <div>
                                    <span>yyyy.mm.dd</span>
                                    <h4>2023년 성탄기념 봉사활동 안내</h4>
                                </div>
                                <img src="/icon-arrow-right.png" alt="버튼 이미지" />
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <div>
                                    <span>yyyy.mm.dd</span>
                                    <h4>2023년 성탄기념 봉사활동 안내</h4>
                                </div>
                                <img src="/icon-arrow-right.png" alt="버튼 이미지" />
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <div>
                                    <span>yyyy.mm.dd</span>
                                    <h4>2023년 성탄기념 봉사활동 안내</h4>
                                </div>
                                <img src="/icon-arrow-right.png" alt="버튼 이미지" />
                            </a>
                        </li>
                    </ul>
                    <ul className="nav">
                        <li>
                            <a href="">
                                <img src="/icon-bell.png" alt="알림 네비게이션" />
                                <p>알림</p>
                            </a>
                        </li>
                        <li>
                            <a href="home.html">
                                <img src="/icon-home.png" alt="홈 네비게이션" />
                                <p>홈</p>
                            </a>
                        </li>
                        <li>
                            <a href="login.html">
                                <img src="/icon-profile.png" alt="마이페이지 네비게이션" />
                                <p>마이페이지</p>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default Home;