<template>
  <div class="root">
    <div class="txt">
      Salve! Quid Agis?
    </div>
  </div>
</template>

<script>
export default {
  name: "user-welcome-page",
}
</script>

<style lang="scss" scoped>

@function makelongshadow($length,$angle) {
  $val: 0px 0px transparent;
  @for $i from 1 through $length {
    $val: #{$val}, #{$i}px #{$i*$angle}px #aaa;
  }
  @return $val;
}

.root {
  width: 100%;
  height: 600px;
  background: radial-gradient(#fff, #aaa);
  //background-size: 100% 100%;
  overflow: hidden;
  padding-top: 300px;
}

.txt {
  text-align: center;
  font-family: 'Roboto', sans-serif;
  font-size: 10vmin;
  font-weight: 700;
  animation: netflix_style 3.5s;
  color: #e90418;
  outline: none;
  white-space: nowrap;
}

@keyframes netflix_style {
  0% {
    text-shadow: makelongshadow(100, 1);
    color: #f3f3f3;
    transform: scale(1.5, 1.5);
  }
  10% {
    text-shadow: makelongshadow(100, 1.5);
    color: #f3f3f3;
    transform: scale(1.5, 1.5);
  }
  15% {
    color: #f3f3f3;
  }
  20% {
    color: #e90418;
    text-shadow: none;
    transform: scale(1.1, 1.1);
  }
  75% {
    opacity: 1;
  }
  80% {
    opacity: 1;
    color: #e90418;
    transform: scale(0.85, 0.9);
  }

  100% {
    opacity: 1;
    color: #e90418;
  }
}

</style>