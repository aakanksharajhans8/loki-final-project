import axios from 'axios';
const api = axios.create({ baseURL: '/api/underwriting', timeout: 10000 });
export const evaluatePolicy = (payload) => api.post('/evaluate', payload).then(r=>r.data);
export const listRules = () => api.get('/rules').then(r=>r.data);
export const createRule = (rule) => api.post('/rules', rule).then(r=>r.data);
export const listDecisions = () => api.get('/decisions').then(r=>r.data);
