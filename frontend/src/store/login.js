const state = {
    isLoggedIn: false
};

const getters = {
    getLoggedIn: (state) => {
        return state.isLoggedIn;
    },
};

const actions = {
    updatelogin: ({ commit }, val) => {
        commit("UPDATE_LOGIN", val);
    },
};

const mutations = {
    UPDATE_LOGIN: (state, val) => {
        state.isLoggedIn = val;
    },
};

export default {
    state,
    getters,
    actions,
    mutations,
};