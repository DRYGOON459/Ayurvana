import api from './api';

export const doshaQuizService = {
  getQuestions: () =>
    api.get('/dosha-quiz/questions'),

  evaluateQuiz: (selectedOptionIds) =>
    api.post('/dosha-quiz/evaluate', { selectedOptionIds }),
};

export default doshaQuizService;
