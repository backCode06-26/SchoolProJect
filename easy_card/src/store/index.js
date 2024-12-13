import { createStore } from "vuex";

// 사용 데이터 type, id, content, x, y, width, height
export default createStore({
  state: {
    index : 0,
    cards : []
  },
  getters: {},
  mutations: {
    addCard(state, type) {
      const id = state.index++;
      const newCard = {
        id: id,
        type: type,
        x: 0,
        y: 0,
        width: '50px',
        height: '50px',
        content: '정보를 입력해주세요',
      }
      state.cards.push(newCard);
    },
    updateCard(state, updateCard) {
      const index = state.cards.findIndex(card => card.id === updateCard.id);

      if(index !== -1) {
        state.cards.splice(index, 1, updateCard);
      }
    },
    removeCard(state, cardId) {
      state.cards = state.cards.filter(card => card.id !== cardId);
    },
  },
  actions: {
    addCard({commit}, type) {
      commit('addCard', type);
    },
    updateCard({commit}, updateCard) {
      commit('updateCard', updateCard);
    },
    removeCard({commit}, cardId) {
      commit('removeCard', cardId);
    }
  },
  modules: {},
});
