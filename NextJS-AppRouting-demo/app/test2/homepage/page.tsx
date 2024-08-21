// @ts-ignore
import styles from './homepage.style.css';

const Home = () => {
    return (
        <div className={styles.screen}>
            <div className="wrap">
                <div className={styles.container}>
                    <div className="notice">
                            <h2>교회 소식</h2>
                            <p>안녕하세요. 이것은 공지글 입니다. 폰트는 프리텐다드 레귤러이고 폰트크기는 14px 입니다.</p>
                    </div>
                </div>
                <div className="main-menu-1">
                    <div className={`${styles.container} ${styles['container-b']}`}>
                        <ul>
                            <li>
                                <a href="">
                                    <h3>오늘의설교</h3>
                                    <div className="icon-img"><img src="/icon-church-1.png" alt="메뉴 아이콘"/></div>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <h3>교회주보</h3>
                                    <div className="icon-img"><img src="/icon-church-2.png" alt="메뉴 아이콘"/></div>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <h3>예배안내</h3>
                                    <div className="icon-img"><img src="/icon-church-3.png" alt="메뉴 아이콘"/></div>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <h3>교회일정</h3>
                                    <div className="icon-img"><img src="/icon-church-4.png" alt="메뉴 아이콘"/></div>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <h3>교회소식</h3>
                                    <div className="icon-img"><img src="/icon-church-5.png" alt="메뉴 아이콘"/></div>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <h3>실시간예배</h3>
                                    <div className="icon-img"><img src="/icon-church-6.png" alt="메뉴 아이콘"/></div>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <h3>교우소식</h3>
                                    <div className="icon-img"><img src="/icon-church-7.png" alt="메뉴 아이콘"/></div>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <h3>교회소개</h3>
                                    <div className="icon-img"><img src="/icon-church-8.png" alt="메뉴 아이콘"/></div>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <h3>홈페이지</h3>
                                    <div className="icon-img"><img src="/icon-church-9.png" alt="메뉴 아이콘"/></div>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
                <ul className="nav">
                    <li>
                            <img src="/icon-bell.png" alt="알림 네비게이션"/>
                            <p>알림</p>
                    </li>
                    <li>
                            <img src="/icon-home.png" alt="홈 네비게이션"/>
                            <p>홈</p>
                    </li>
                    <li>
                            <img src="/icon-profile.png" alt="마이페이지 네비게이션"/>
                            <p>마이페이지</p>
                    </li>
                </ul>
            </div>
        </div>
    );
};

export default Home;