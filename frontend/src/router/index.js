import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/Documents",
    name: "Documents",

    component: () => import("../views/Documents.vue")
  },
  {
    path: "/QA",
    name: "QA",
    component: () => import("../views/QA.vue")
  },
  {
    path: "/Shop",
    name: "Shop",

    component: () => import("../views/Shop.vue")
  },
  {
    path: "/view_Question/:id",
    name: "view_Question",
    component: () => import("../views/view_Question.vue")
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("../views/Login.vue")
  },
  {
    path: "/newQuestion",
    name: "newQuestion",
    component: () => import("../views/newQuestion.vue")
  }
];

const router = new VueRouter({
  routes
});

export default router;
