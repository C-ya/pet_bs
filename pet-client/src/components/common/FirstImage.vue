<template>
    <div>
<!-- 首图 -->
    <div class="hero-slide-item swiper-slide">
      <div class="hero-slide-bg">
        <img
          src="@/assets/img/images/slider1-1.png"
          alt="Slider Image"
        />
      </div>
      <div class="container">
        <div class="hero-slide-content text-center text-md-end">
          <h5 class="sub-title">欢迎来到宠物寄存中心！</h5>
          <h2 class="title m-0">一切为了您的爱宠</h2>
          <p class="ms-0">
            在这里发布你的寄存需求、萌宠的日常、分享您的爱宠与心得！
          </p>
        </div>
      </div>
    </div>
    <!-- 首图 End -->
    </div>
</template>

<script>
export default {
   data() {
      return {
      }
   }
}
</script>

<style>
.text-center {
  text-align: center !important;
}
.text-md-end {
  text-align: right !important;
}
.hero-slide-content {
  position: relative;
  z-index: 9;
}

.hero-slide-content .sub-title {
  font-size: 26px;
  font-weight: 400;
  color: #000000;
}

@media only screen and (min-width: 768px) and (max-width: 991px) {
  .hero-slide-content .sub-title {
    font-size: 20px;
  }
}

@media only screen and (max-width: 767px) {
  .hero-slide-content .sub-title {
    font-size: 18px;
  }
}

.hero-slide-content .title {
  font-size: 46px;
  font-weight: 600;
  margin-bottom: 10px;
  line-height: 46px;
  color: #000000;
}

@media only screen and (min-width: 992px) and (max-width: 1199px) {
  .hero-slide-content .title {
    font-size: 40px;
    margin-bottom: 25px;
    line-height: 1.1;
  }
}

@media only screen and (min-width: 768px) and (max-width: 991px) {
  .hero-slide-content .title {
    font-size: 30px;
    margin-bottom: 20px;
    line-height: 1.1;
  }
}

@media only screen and (max-width: 767px) {
  .hero-slide-content .title {
    font-size: 30px;
    margin-bottom: 15px;
    line-height: 1.1;
  }
}

@media only screen and (max-width: 479px) {
  .hero-slide-content .title {
    font-size: 26px;
    margin-bottom: 10px;
  }
}

.hero-slide-content p {
  line-height: 1.3;
  font-size: 16px;
  font-weight: 400;
  margin-bottom: 0px;
  margin-top: 20px;
  color: #000000;
}

@media only screen and (max-width: 767px) {
  .hero-slide-content p {
    font-size: 14px;
    line-height: 1.4;
    width: 270px;
    margin: 10px auto 0;
  }
}

.hero-slide-content .btn {
  margin-top: 40px;
  padding: 20px 45px;
  border-radius: 0px;
}

@media only screen and (min-width: 768px) and (max-width: 991px),
  only screen and (max-width: 767px) {
  .hero-slide-content .btn {
    font-size: 14px;
    padding: 15px 25px;
  }
}

@media only screen and (max-width: 767px) {
  .hero-slide-content .btn {
    margin-top: 30px;
  }
}

@media only screen and (max-width: 479px) {
  .hero-slide-content .btn {
    font-size: 12px;
    padding: 10px 18px;
    margin-top: 26px;
  }
}
.container {
  padding-right: var(--bs-gutter-x, 15px);
  padding-left: var(--bs-gutter-x, 15px);
}

@media only screen and (min-width: 1600px),
  only screen and (min-width: 1200px) and (max-width: 1599px) {
  .container {
    max-width: 1200px;
  }
}

.hero-slide-item {
  position: relative;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -webkit-align-items: center;
  -ms-flex-align: center;
  align-items: center;
  height: 610px;
}

@media only screen and (min-width: 1200px) and (max-width: 1599px),
  only screen and (min-width: 992px) and (max-width: 1199px) {
  .hero-slide-item {
    height: 550px;
  }
}

@media only screen and (min-width: 768px) and (max-width: 991px) {
  .hero-slide-item {
    height: 450px;
  }
}

@media only screen and (max-width: 767px) {
  .hero-slide-item {
    height: 350px;
  }
}

@media only screen and (max-width: 479px) {
  .hero-slide-item {
    height: 300px;
  }
}

.m-0 {
  margin: 0 !important;
}
.ms-0 {
  margin-left: 0 !important;
}
.hero-slide-bg {
  position: absolute;
  z-index: 1;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: #ebebeb;
}
.hero-slide-bg img {
  width: 100%;
  height: 100%;
  -o-object-fit: cover;
  object-fit: cover;
}

</style>
