<template>
  <div>
    <!-- start -->
    <div class="start scroll-ani">
      <a @click="scroll" href="#function1" style="z-index: 1000"><span></span>Scroll</a>

      <div class="header">
        <!-- wave-header -->
        <div class="inner-header flex">
          <div class="header-text">
            <img class="logo-img" src="~assets/logo/logo_white.png" alt="">
            <span class="logo-explain">똑똑하게 저장하고 나누는 방법</span>
            <google-login-button color="white"/>
            <div style="margin: 20px 0 0" class="row justify-center">
              <q-btn
                @click="goToExtension"
                rounded
                flat
                size="12px"
                stack 
                label="크롬 익스텐션"
                icon="img:data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHg9IjBweCIgeT0iMHB4Igp3aWR0aD0iMzAiIGhlaWdodD0iMzAiCnZpZXdCb3g9IjAgMCAxNzIgMTcyIgpzdHlsZT0iIGZpbGw6IzAwMDAwMDsiPjxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0ibm9uemVybyIgc3Ryb2tlPSJub25lIiBzdHJva2Utd2lkdGg9IjEiIHN0cm9rZS1saW5lY2FwPSJidXR0IiBzdHJva2UtbGluZWpvaW49Im1pdGVyIiBzdHJva2UtbWl0ZXJsaW1pdD0iMTAiIHN0cm9rZS1kYXNoYXJyYXk9IiIgc3Ryb2tlLWRhc2hvZmZzZXQ9IjAiIGZvbnQtZmFtaWx5PSJub25lIiBmb250LXdlaWdodD0ibm9uZSIgZm9udC1zaXplPSJub25lIiB0ZXh0LWFuY2hvcj0ibm9uZSIgc3R5bGU9Im1peC1ibGVuZC1tb2RlOiBub3JtYWwiPjxwYXRoIGQ9Ik0wLDE3MnYtMTcyaDE3MnYxNzJ6IiBmaWxsPSJub25lIj48L3BhdGg+PGcgZmlsbD0iI2ZmZmZmZiI+PHBhdGggZD0iTTg2LDE3LjJjLTIyLjUwOTA3LDAgLTQyLjQzNzI0LDEwLjg2MjA3IC01NC45ODE3NywyNy41ODA0N2wyNC44OTI5NywyNC44OTI5N2M1LjgyNTA3LC0xMC43MTU2IDE3LjAzOTc0LC0xOC4wNzM0NCAzMC4wODg4LC0xOC4wNzM0NGM3LjY5NDEzLDAgMTQuNzI1MjYsMi42MDk5MiAyMC40NTg1OSw2Ljg3NTUyaDQxLjQ2NTg5bC0zMS4xNDE0LDEyLjQ1MjA4YzIuMjQxNzMsNC41Njk0NyAzLjYxNjkyLDkuNjQyOTMgMy42MTY5MiwxNS4wNzIzOWMwLDExLjMwNjEzIC01LjUzMDYxLDIxLjI1Nzk0IC0xMy45NDE0MSwyNy41MjQ0OGgwLjE3OTE3bC0yNy4yODkzMiw0MC45Mzk1OGMyLjE4NDQsMC4yMTIxMyA0LjQwNDA5LDAuMzM1OTQgNi42NTE1NiwwLjMzNTk0YzM4LjAwMDUzLDAgNjguOCwtMzAuNzk5NDcgNjguOCwtNjguOGMwLC0zOC4wMDA1MyAtMzAuNzk5NDcsLTY4LjggLTY4LjgsLTY4Ljh6TTMwLjk5NTgzLDQ0Ljc5MTY3Yy04LjYyMjkzLDExLjQ5NTMzIC0xMy43OTU4MywyNS43MjgzMyAtMTMuNzk1ODMsNDEuMjA4MzNjMCwzNS43MTI5MyAyNy4yMTc5Miw2NS4wNTMgNjIuMDM2NDYsNjguNDUyODZsMTEuNTExNDYsLTM0LjUzNDM3Yy0xLjU3MDkzLDAuMjE3ODcgLTMuMTI1MzgsMC40ODE1MSAtNC43NDc5MiwwLjQ4MTUxYy0xOC45OTQ1MywwIC0zNC40LC0xNS40MDU0NyAtMzQuNCwtMzQuNHpNODYsNjMuMDY2NjdjLTEyLjY2NTczLDAgLTIyLjkzMzMzLDEwLjI2NzYgLTIyLjkzMzMzLDIyLjkzMzMzYzAsMTIuNjY1NzMgMTAuMjY3NiwyMi45MzMzMyAyMi45MzMzMywyMi45MzMzM2MxMi42NjU3MywwIDIyLjkzMzMzLC0xMC4yNjc2IDIyLjkzMzMzLC0yMi45MzMzM2MwLC0xMi42NjU3MyAtMTAuMjY3NiwtMjIuOTMzMzMgLTIyLjkzMzMzLC0yMi45MzMzM3oiPjwvcGF0aD48L2c+PC9nPjwvc3ZnPg=="
              />
              <q-btn 
                @click="goToApk"
                rounded
                flat
                size="12px"
                stack
                label="안드로이드 앱"
                icon="android"
              />
            </div>
          </div>

          <div class="header-img"></div>
        </div>

        <!-- wave -->
        <div>
          <svg
            class="waves"
            xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            viewBox="0 24 150 28"
            preserveAspectRatio="none"
            shape-rendering="auto"
          >
            <defs>
              <path
                id="gentle-wave"
                d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z"
              />
            </defs>
            <g class="parallax">
              <use
                xlink:href="#gentle-wave"
                x="48"
                y="0"
                fill="rgba(255,255,255,0.7"
              />
              <use
                xlink:href="#gentle-wave"
                x="48"
                y="3"
                fill="rgba(255,255,255,0.5)"
              />
              <use
                xlink:href="#gentle-wave"
                x="48"
                y="5"
                fill="rgba(255,255,255,0.3)"
              />
              <use xlink:href="#gentle-wave" x="48" y="7" fill="#fff" />
            </g>
          </svg>
        </div>
        <div class="white-margin"></div>
      </div>
    </div>

    <div class="function-container">
      <!-- url입력/태그 추천 -->
      <div id="function1" class="function-box function-box-up">
        <div class="row justify-center">
          <div class="img-box">
            <img :src="img[0]" alt="" id="img-first" />
          </div>
          <div class="text-box">
            <p>맞춤형 태그와 url을 제공받으세요.</p>
            <span class="sub-title"
              >Urls는 사용자 맞춤형 서비스를 제공합니다.</span
            >
            <span class="sub-title"
              >url을 저장하여 당신의 성향과 추천 태그, url을 알아보세요.</span
            >

            <div class="explain-box">
              <details @click="explainOnOff(0)" open>
                <summary>간편하게 url 저장하기</summary>
                <p>
                  <span>Urls에 폴더를 생성하고 url을 저장해보세요.</span>
                  <br />
                  <span
                    >크롬 확장 프로그램을 이용하여 간편하게 저장할 수도
                    있습니다.</span
                  >
                </p>
              </details>
              <details @click="explainOnOff(1)">
                <summary>url의 태그 추천받기</summary>
                <p>
                  <span>url을 입력하면 자동으로 태그를 추천해드립니다.</span>
                  <br />
                  <span>태그를 추천받아 url을 효율적으로 관리하세요.</span>
                </p>
              </details>
              <details @click="explainOnOff(2)">
                <summary>맞춤형 url 제공받기</summary>
                <p>
                  <span>당신이 저장한 url을 기반으로 성향을 분석합니다.</span>
                  <br />
                  <span
                    >또한, 성향을 분석하여 당신에게 알맞은 url을
                    추천해드립니다.</span
                  >
                </p>
              </details>
            </div>
          </div>
        </div>
      </div>

      <!-- 공유/메모/하이라이트 -->
      <div id="function2" class="function-box function-box-up">
        <div class="row justify-center">
          <div class="img-box small-display-on">
            <img :src="img[3]" alt="" id="img-second1" />
          </div>
          <div class="text-box">
            <p>팀원과 url을 공유하세요.</p>
            <span class="sub-title">Urls는 공유 폴더 기능을 제공합니다.</span>
            <span class="sub-title"
              >팀원과 함께 url을 공유하고 폴더를 편집해보세요.</span
            >
            <div class="explain-box">
              <details @click="explainOnOff(3)" open>
                <summary>공유 폴더 생성하기</summary>
                <p>
                  <span
                    >Urls에서는 개인 폴더 혹은 공유 폴더를 생성할 수
                    있습니다.</span
                  >
                  <br />
                  <span
                    >개인 폴더에 팀원을 초대하면 자동으로 공유 폴더가
                    됩니다.</span
                  >
                </p>
              </details>
              <details @click="explainOnOff(4)">
                <summary>팀원과 url 공유하기</summary>
                <p>
                  <span>공유 폴더에 팀원을 초대하고 권한을 부여해보세요. </span>
                  <br />
                  <span
                    >공유 폴더에 소속된 팀원끼리는 url을 공유할 수
                    있습니다.</span
                  >
                </p>
              </details>
              <details @click="explainOnOff(5)">
                <summary>메모·형광펜 기능 활용하기</summary>
                <p>
                  <span>폴더에 중요한 내용을 메모하여 공유할 수 있습니다.</span>
                  <br />
                  <span
                    >url 페이지에서 형광펜 칠한 내용을 공유할 수도 있습니다.
                  </span>
                </p>
              </details>
            </div>
          </div>
          <div class="img-box small-display-off">
            <img :src="img[3]" alt="" id="img-second2" />
          </div>
        </div>
      </div>

      <!-- 플랫폼 -->
      <div id="function3" class="function-box function-box-up">
        <div class="row justify-center">
          <div class="img-box">
            <img :src="img[6]" alt="" id="img-third" />
          </div>
          <div class="text-box">
            <p>다양한 플랫폼에서 활용하세요.</p>
            <span class="sub-title">Urls는 다양한 플랫폼을 제공합니다.</span>
            <span class="sub-title"
              >홈페이지, 앱, 크롬 확장 프로그램을 활용해보세요.</span
            >

            <div class="explain-box">
              <details @click="explainOnOff(6)" open>
                <summary>PC 홈페이지</summary>
                <p>
                  <span>핸드폰 혹은 PC에서 Urls 홈페이지에 접속해보세요.</span>
                  <br />
                  <span
                    >반응형으로 최적화된 홈페이지는 효율적인 url 관리를
                    도와줍니다.</span
                  >
                </p>
              </details>
              <details @click="explainOnOff(7)">
                <summary>모바일 앱</summary>
                <p>
                  <span>앱스토어에 접속하여 Urls 앱을 설치해보세요.</span>
                  <br />
                  <span
                    >어디에서든 핸드폰을 이용하여 Urls를 활용할 수
                    있습니다.</span
                  >
                </p>
              </details>
              <details @click="explainOnOff(8)">
                <summary>크롬 확장 프로그램</summary>
                <p>
                  <span>Urls는 크롬 확장 프로그램을 지원합니다.</span>
                  <br />
                  <span
                    >몇 번의 클릭만으로 더 간단하게 url을 관리해보세요.</span
                  >
                </p>
              </details>
            </div>
          </div>
        </div>
      </div>
    </div>

    <q-page-scroller position="bottom-right" :scroll-offset="150" :offset="[18, 18]">
      <q-btn fab icon="keyboard_arrow_up" color="primary" />
    </q-page-scroller>
  </div>

</template>

<script>
import GoogleLoginButton from '../../components/buttons/GoogleLoginButton.vue'
import { ref, onMounted } from "vue";
import { openURL } from 'quasar'

export default {
  components: { GoogleLoginButton },
  setup() {
    onMounted(() => {
      const saTriggerMargin = 250;
      const saElementList = document.querySelectorAll('.function-box');

      const saFunc = function() {
        for (const element of saElementList) {
          if (!element.classList.contains('function-box-show')) {
            if (window.innerHeight > element.getBoundingClientRect().top + saTriggerMargin) {
              element.classList.add('function-box-show');
            }
          }
        }
      }

      window.addEventListener('load', saFunc);
      window.addEventListener('scroll', saFunc);
    }); 

    const img = [
      require("../../assets/explain/1.png"),
      require("../../assets/explain/2.png"),
      require("../../assets/explain/3.png"),
      require("../../assets/explain/4.png"),
      require("../../assets/explain/5.png"),
      require("../../assets/explain/6.png"),
      require("../../assets/explain/7.png"),
      require("../../assets/explain/8.png"),
      require("../../assets/explain/9.png"),
    ];

    const scroll = event => {
      event.preventDefault();
      window.scrollTo({ top: window.innerHeight, behavior: "smooth" });
    };

    const explainOnOff = imgNum => {
      var details = document.getElementsByTagName("details");
      if (imgNum < 3) {
        details[0].removeAttribute("open");
        details[1].removeAttribute("open");
        details[2].removeAttribute("open");
        document.getElementById("img-first").src = img[imgNum];
      } else if (imgNum < 6) {
        details[3].removeAttribute("open");
        details[4].removeAttribute("open");
        details[5].removeAttribute("open");
        document.getElementById("img-second1").src = img[imgNum];
        document.getElementById("img-second2").src = img[imgNum];
      } else {
        details[6].removeAttribute("open");
        details[7].removeAttribute("open");
        details[8].removeAttribute("open");
        document.getElementById("img-third").src = img[imgNum];
      }
    };

    const goToApk = () => {
      openURL("https://drive.google.com/file/d/1HcHA_76-MXtGzML7XIWQFabDVyqx6OgJ/view?usp=sharing");
    };

    const goToExtension = () => {
      openURL("https://drive.google.com/file/d/1iSKSFrTiIACSBrpY24Jts6y4R_yFuczU/view?usp=sharing");
    };

    return {
      slide: ref(1),
      autoplay: ref(true),
      scroll,
      explainOnOff,
      img,
      goToExtension,
      goToApk
    };
  }
};
</script>

<style scoped lang="scss">
$primary: #fd687ec5;
$secondary: #7944a5;
$tertiary: #ff538f;

// start css start
.start {
  position: relative;
  width: 99vw;
  height: calc(100vh - 50px);
  margin: 0 auto;
  // &::after {
  //   position: absolute;
  //   bottom: 0;
  //   left: 0;
  //   content: '';
  //   width: 100%;
  //   height: 80%;
  //   background: -webkit-linear-gradient(top,rgba(0,0,0,0) 0,rgba(0,0,0,.8) 80%,rgba(0,0,0,.8) 100%);
  //   background: linear-gradient(to bottom,rgba(0,0,0,0) 0,rgba(0,0,0,.8) 80%,rgba(0,0,0,.8) 100%);
  // }
}
.scroll-ani a {
  padding-top: 50px;
  position: absolute;
  bottom: 0px;
  left: 50%;
  z-index: 2;
  display: inline-block;
  -webkit-transform: translate(0, -50%);
  transform: translate(0, -50%);
  color: $dark;
  letter-spacing: 0.1em;
  text-decoration: none;
  transition: opacity 0.3s;
  cursor: pointer;
  a:hover {
    opacity: 0.5;
  }
  span {
    position: absolute;
    top: 0;
    left: 50%;
    width: 12px;
    height: 12px;
    margin-left: -6px;
    border-left: 2px solid $dark;
    border-bottom: 2px solid $dark;
    -webkit-transform: rotate(-45deg);
    transform: rotate(-45deg);
    -webkit-animation: sdb05 1.5s infinite;
    animation: sdb05 1.5s infinite;
    box-sizing: border-box;
  }
  @-webkit-keyframes sdb05 {
    0% {
      -webkit-transform: rotate(-45deg) translate(0, 0);
      opacity: 0;
    }
    50% {
      opacity: 1;
    }
    100% {
      -webkit-transform: rotate(-45deg) translate(-20px, 20px);
      opacity: 0;
    }
  }
  @keyframes sdb05 {
    0% {
      transform: rotate(-45deg) translate(0, 0);
      opacity: 0;
    }
    50% {
      opacity: 1;
    }
    100% {
      transform: rotate(-45deg) translate(-20px, 20px);
      opacity: 0;
    }
  }
}
// start css end

// wave css start
.header {
  position: relative;
  text-align: center;
  background: linear-gradient(60deg, #9b439e, #fd6a7f);
  color: white;
}
.inner-header {
  height: 58vh;
}
.white-margin {
  position: absolute;
  background-color: white;
  width: 100%;
  height: 30vh;
  z-index: 10;
}
.header-img {
  background-image: url("../../assets/explain/10.png");
  background-repeat : no-repeat;
  background-size : cover;
  width: 50vw;
  height: 73vh;
  margin-left: 11vw;
}
.header-text {
  display: flex;
  flex-direction: column;
  .logo-img {
    width: 280px
  }
  .logo-explain { 
    letter-spacing: 4px;
    margin-top: 15px;
    margin-bottom: 4vh;
    font-weight: 300;
  }
  button {
    margin-top: 8px;
    margin-left: 5px;
  }
}
.flex {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  text-align: center;
}
.waves {
  position: relative;
  width: 100%;
  height: 20vh;
  margin-bottom: -7px;
  min-height: 100px;
  max-height: 150px;
}
.parallax {
  & > use {
    animation: move-forever 25s cubic-bezier(0.55, 0.5, 0.45, 0.5) infinite;
  }

  & > use:nth-child(1) {
    animation-delay: -2s;
    animation-duration: 7s;
  }

  & > use:nth-child(2) {
    animation-delay: -3s;
    animation-duration: 10s;
  }

  & > use:nth-child(3) {
    animation-delay: -4s;
    animation-duration: 13s;
  }

  & > use:nth-child(4) {
    animation-delay: -5s;
    animation-duration: 20s;
  }
  @keyframes move-forever {
    0% {
      transform: translate3d(-90px, 0, 0);
    }
    100% {
      transform: translate3d(85px, 0, 0);
    }
  }
}
@media (max-width: 800px) {
  .waves {
    height: 40px;
    min-height: 40px;
  }
  .flex {
    justify-content: center;
  }
  .header-img {
    display: none;
  }
  .logo-img {
    font-size: 24px;
  }
}
// wave css end

// --------------------------------------------------------------
// function css
.function-container {
  width: 95vw;
  margin: 0 auto;
}
.function-box {
  display: flex;
  justify-content: center;
  margin: 16vw 0px;
  opacity: 0;
  transition: all .8s ease;
}
.function-box-up {
  transform: translate(0, 100px);
}
.function-box-show {
  opacity: 1;
  transform: none;
}
.small-display-off {
  display: block;

}
.small-display-on {
  display: none;

}
@media (max-width: 1294px) {
.small-display-off {
  display: none;
}
.small-display-on {
  display: block;
}
}
.img-box {
  margin: 10px 0px;
  img {
    padding: 0px 20px;
    max-width: 100%; 
    height: auto;
    object-fit: cover;
  }
}
.text-box {
  margin: 20px 40px 0px 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  .sub-title {
    font-size: 14px;
    font-weight: 300;
  }
  & > p:nth-child(1) {
    font-size: 24px;
    font-weight: 600;
    margin-bottom: 5px;
  }
  & > span:nth-child(3) {
    margin-bottom: 20px;
  }
}

// expend css
.explain-box {
  width: 450px;
  height: 376px;

  @mixin states($color: $primary) {
    border-color: $color;
    color: rgb(255, 255, 255);
    background: linear-gradient(50deg, $tertiary, $primary);

    &:hover {
      border-color: $color;
      color: rgba(255, 255, 255, 0.6);
    }
    &:active {
      color: rgba(255, 255, 255, 0.9);
      border-color: darken($color, 15%);
    }
  }

  details {
    @include states($primary);
    border: 1px solid;
    cursor: pointer;
    border-radius: 6px;
    min-height: 48px;
    max-height: 60px;
    transform-origin: top center;
    transform: rotate(0.1deg);
    transition: all 0.3s;
    // box-shadow: 1px 1px 1px 1px rgb(219, 219, 219);

    ::-webkit-details-marker {
      display: none;
    }

    + details {
      margin-top: 10px;
    }

    p {
      border-top: 1px dashed rgb(255, 255, 255);
      color: #fefefe;
      padding: 15px 20px 0px;
    }

    &[open] {
      transition: all 0.6s;
      min-height: 100px;
      max-height: 200px;
    }
  }
}
summary {
  outline: none;
  font-size: 16px;
  padding: 13px;

  .close {
    display: none;

    [open] & {
      display: inline;
    }
  }

  .open {
    display: inline;

    [open] & {
      display: none;
    }
  }

  [open] & {
    display: inline;
  }

  &:after {
    content: "+";
    float: left;
    margin-right: 11px;
    width: 20px;
    text-align: center;
    font-size: 14px;

    [open] & {
      content: "-";
    }
  }
}
// --------------------------------------------------------------

</style>
