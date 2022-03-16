<template>
  <div v-show="isShow" class="root">
    <div class="alert" :class="type">
      <div class="flex">{{ msg }}</div>
      <div class="space-around" v-if="type === 'alert'">
        <div class="btnCommon success" @click="close">Confirm</div>
      </div>
      <div class="space-around" v-else-if="type === 'confirm'">
        <div class="btnCommon cancel" @click="cancelEvent">Cancel</div>
        <div class="btnCommon success" @click="successEvent">Confirm</div>
      </div>

    </div>
    <div class="mask" @click="closeMask" v-if="type !== 'msg'"></div>
  </div>
</template>

<script>
export default {
  name: 'Alert',
  props: {
    // 提示信息
    msg: {
      type: String,
      default: ''
    },
    // 是否显示提示框
    isShow: {
      type: Boolean,
      default: false
    },
    // 插件类型：alert/confirm
    type: {
      type: String,
      default: 'alert'
    },
    // confirm插件接收的确认事件
    success: {
      type: Function,
      default: () => {
        console.log('点击了success');
      }
    },
    // confirm插件接收的取消事件
    cancel: {
      type: Function,
      default: () => {
        console.log('点击了cancel');
      }
    }
  },
  methods: {
    // 关闭弹窗
    close() {
      this.isShow = false
    },
    // alert 插件点击阴影区域关闭弹窗
    closeMask() {
      if (this.type === 'alert') {
        this.close();
      }
    },
    // confirm 插件点击取消触发的事件
    cancelEvent() {
      this.cancel();
      this.close();
    },
    // confirm 插件点击确定触发的事件
    successEvent() {
      this.success();
      this.close();
    }
  },
  updated() {
    var _this = this;
    if (_this.type == 'msg') {
      setTimeout(function () {
        _this.close()
      }, 1500);
    }
  }
}

</script>
<style lang="stylus" rel="stylesheet/stylus" scoped>

.root {

  position: fixed;
  top: 50%;

  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}

.alert {
  min-width 350px;
  width max-content;
  height: auto;
  margin-left auto;
  margin-right auto;
  margin-top: -75px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 5px 8px rgba(0, 0, 0, .05);
  z-index: 3000;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  font-family: Roboto, sans-serif;
}

.msg {
  background-color: rgba(0, 0, 0, 0);
  box-shadow: none;

  font-size: 18px;
  font-family: Roboto, sans-serif;
}

.msg .flex {
  padding: 20px 40px !important;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 10px 10px 18px rgba(0, 0, 0, .2);
}

.flex {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 30px;
  word-break: break-all;
  max-width 400px;
  overflow: word-wrap ;
  line-height: 40px;
}

.space-around {
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  border-top: 1px solid #cfcfcf;
}

.btnCommon {
  width: 250px;
  height: 92px;
  line-height: 92px;
  text-align: center;
  border-radius: 6px;
  font-size: 20px;

  &.success {
    background-color: $btnMain;
    color: green;
    cursor: pointer;

    &:hover {
      background-color: $btnDark;
    }
  }

  &.cancel {
    width: 249px;
    color: #333;
    cursor: pointer;
    border-right: 1px solid #cfcfcf;
  }
}

.mask {
  position: fixed;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, .4);
  left: 0;
  top: 0;
  overflow: hidden;
  z-index: 2000;
}

.msg .mask {
  display: none;
}
</style>