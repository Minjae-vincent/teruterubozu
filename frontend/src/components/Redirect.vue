<template>
  <div></div>
</template>

<script>
export default {
  created() {
    const token = this.$route.query.token;
    if (token) {
      localStorage.setItem('accessToken', token);
      alert('로그인하였습니다');
      this.$router.push('/main');
    } else {
      window.alert('로그인에 실패하였습니다.');
      this.$router.push('/sign-in');
    }
  },
};
</script>

<style scoped></style>
