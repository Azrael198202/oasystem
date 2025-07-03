<template>
    <div>
        <div class="footer-wrap">
            <div class="footer-main">
                <span class="span-left">
                    V 1.8.2
                </span>
                <span class="span-right">
                    © Copyright 株式会社 リーディング・ウィン All Rights Reseved.
                </span>
            </div>
        </div>
    </div>
</template>

<script>
export default {

}
</script>

<style lang='scss'>
.footer-wrap {
  width: 100%;
  min-width: 1300px;
  height: 50px;
  background-color: #fff;
  box-shadow: 0 -3px 23px 0 #95959530;
  z-index: 3000 !important;
  position: fixed;
  bottom: 0;

  .footer-main {
      width: 1300px;
      margin: 0 auto;

      .span-left {
          line-height: 50px;
          font-size: 12px;
          float: left;
      }

      .span-right {
          line-height: 50px;
          font-size: 12px;
          float: right;
      }
  }
}
</style>