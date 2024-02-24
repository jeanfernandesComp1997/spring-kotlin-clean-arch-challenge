import http from 'k6/http';
import { check, sleep } from 'k6';
export const options = {
  vus: 50,
  duration: '300s',
};

//export default function () {
//    let data = {
//                   "name": "John Doe",
//                   "document": Math.floor(Math.random() * 999999999999999),
//                   "birthDate": "1997-04-30",
//                   "email": `${Math.floor(Math.random() * 999999999999999)}@email.com`,
//                   "password": "87559Nnkjnfsd%!("
//               };
//
//     let res = http.post('http://172.21.48.1:8080/customers', JSON.stringify(data), {
//        headers: { 'Content-Type': 'application/json' },
//      });
//  check(res, { 'status was 201': (r) => r.status == 201 });
//}

export default function () {
     let res = http.get('http://localhost:8080/customers/00b3eaa7-a684-4f30-a998-87f0c23bd321/no-restrictions');
  check(res, { 'status was 200': (r) => r.status == 200 });
}