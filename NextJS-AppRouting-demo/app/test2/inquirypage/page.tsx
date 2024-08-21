// @ts-ignore
import styles from '@/app/test2/inquirypage/inquiry.style.css';

const Home = () => {
    return (
        <div className={styles.screen}>
            <div className="wrap">
                <div className={`${styles.container} ${styles['container-b']}`}>
                    <div className="appbar">
                        <button>
                            <img src="/icon-arrow-back.png" alt="뒤로가기 버튼"/>
                        </button>
                        <div className="title">소리함</div>
                    </div>
                    <div>
                        {/* 탭메뉴 */}
                        <ul className="tab-title">
                            <li className="tab-item active"><a href="#tab1">문의하기</a></li>
                            <li className="tab-item"><a href="#tab2">문의내역확인</a></li>
                        </ul>
                        {/* 탭 컨텐츠 */}
                        <div className="tab-content-wrap">
                            <div id="tab1" className="tab-content active">
                                <div className="inquiry-1">
                                    <select name="" id="">
                                        <option value="">일반 문의</option>
                                        <option value="">상담 문의</option>
                                        <option value="">협업 문의</option>
                                        <option value="">기타</option>
                                    </select>
                                    <div>
                                        <input
                                            type="text"
                                            name=""
                                            id=""
                                            placeholder="제목을 입력해주세요."
                                        />
                                    </div>
                                    <textarea
                                        name=""
                                        id=""
                                        placeholder="문의 내용을 입력해주세요."
                                    ></textarea>
                                    <div className="filebox">
                                        <input
                                            className="upload-name"
                                            value="첨부파일"
                                            readOnly
                                            placeholder="첨부파일"
                                        />
                                        <label htmlFor="file">
                                            <img src="/icon_file.png" alt="파일 첨부 아이콘"/>
                                        </label>
                                        <input type="file" id="file"/>
                                    </div>
                                    <div className="terms-wrap">
                                        <div className="input-c-1">
                                            <input type="checkbox" id="idsave"/>
                                            <label htmlFor="idsave">
                                                <span>[필수] 개인정보 수집 및 이용동의</span>
                                            </label>
                                        </div>
                                        <div className="terms"></div>
                                    </div>
                                    <div className="btn btn-b1">
                                        <button>문의하기</button>
                                    </div>
                                </div>
                            </div>

                            <div id="tab2" className="tab-content">
                                <div className="novalue">
                                    <p>문의 내역이 없습니다</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <ul className="nav">
                        <li>
                            <a href="">
                                <img src="/icon-bell.png" alt="알림 네비게이션"/>
                                <p>알림</p>
                            </a>
                        </li>
                        <li>
                            <a href="home.html">
                                <img src="/icon-home.png" alt="홈 네비게이션"/>
                                <p>홈</p>
                            </a>
                        </li>
                        <li>
                            <a href="login.html">
                                <img src="/icon-profile.png" alt="마이페이지 네비게이션"/>
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