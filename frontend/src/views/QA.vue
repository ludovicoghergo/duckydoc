<template>
  <div class="QA mt-2">
    <div class="top_search text-center" align-center>
      <h2>Search your doubt...</h2>
      <v-text-field
        v-model="searchQuestion"
        placeholder="insert some keywords"
        solo
        rounded
        class="text_question justify-center"
      ></v-text-field>
    </div>
    <div class="left_QA">
      <Questions n_page="0" />
    </div>
    <div class="right_QA">
      <Questions n_page="2" />
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import Questions from "@/components/Questions.vue";

export default {
  name: "QA",
  components: {
    Questions,
  },
  data() {
    return {
      searchQuestion: "",
    };
  },
};
</script>
<style scoped>
.top_search {
  width: 50%;
  margin-left: auto;
  margin-right: auto;
}
.QA {
  width: 100%;
}
.left_QA {
  display: block;
  width: 50%;
  float: left;
}
.right_QA {
  display: block;
  width: 50%;
  float: right;
}
.text_question {
  width: 100% !important;
}
</style>
