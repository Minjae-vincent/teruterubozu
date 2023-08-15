<template>
  <v-card class="mx-auto" style="max-width: 500px">
    <v-toolbar color="deep-purple-accent-4" cards dark flat>
      <v-btn icon>
        <router-link to="/">
          <v-icon color="white">mdi-arrow-left</v-icon>
        </router-link>
      </v-btn>
      <v-card-title class="text-h6 font-weight-regular"> Sign up </v-card-title>
      <v-spacer></v-spacer>
    </v-toolbar>
    <v-form ref="form" v-model="isValid" class="pa-4 pt-6">
      <v-text-field
        v-model="name"
        variant="filled"
        color="deep-purple"
        label="Name"
      ></v-text-field>
      <v-text-field
        v-model="email"
        :rules="[rules.email]"
        variant="filled"
        color="deep-purple"
        label="Email address"
        type="email"
      ></v-text-field>
      <v-text-field
        v-model="password"
        :rules="[rules.password, rules.length(6)]"
        variant="filled"
        color="deep-purple"
        counter="6"
        label="Password"
        style="min-height: 96px"
        type="password"
      ></v-text-field>
    </v-form>
    <v-divider></v-divider>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn
        :disabled="!isValid"
        :loading="isLoading"
        color="deep-purple-accent-4"
        @click="signUp()"
      >
        Submit
      </v-btn>
    </v-card-actions>
  </v-card>
</template>
<script>
import axios from 'axios';

export default {
  name: 'SignUp',
  data: () => ({
    email: undefined,
    isValid: false,
    isLoading: false,
    password: undefined,
    name: undefined,
    rules: {
      email: (v) => !!(v || '').match(/@/) || 'Please enter a valid email',
      length: (len) => (v) =>
        (v || '').length >= len || `Invalid character length, required ${len}`,
      password: (v) =>
        !!(v || '').match(
          /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(_|[^\w])).+$/
        ) ||
        'Password must contain an upper case letter, a numeric character, and a special character',
      required: (v) => !!v || 'This field is required',
    },
  }),
  methods: {
    signUp() {
      this.isLoading = true;
      axios
        .post('http://localhost:9000/api/user/signup', {
          email: this.email,
          password: this.password,
          name: this.name,
        })
        .then((res) => {
          this.isLoading = false;
          console.log(res);
          this.$router.push('/');
        })
        .catch((err) => {
          alert(err.response.data.message);
          this.isLoading = false;
        });
    },
  },
};
</script>
