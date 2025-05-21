/**
 * 반도체 설계
 * 꼬이지 않으려면 앞의 포트부터 연결할 때, 가장 최근에 연결한 포트보다 작은 포트를 연결하면 안된다.
 * 따라서 가장 긴 증가하는 수열의 길이
 */

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const n = Number(input[0]);
const ports = input[1].split(" ").map(Number);
const lis = [];

for (let i = 0; i < ports.length; i++) {
  // lis
  setArr(lis, ports[i]);
}
console.log(lis.length);

function setArr(arr, v) {
  let idx = lowerBound(arr, v);
  if (idx === arr.length) {
    arr.push(v);
  } else {
    arr[idx] = v;
  }
}

function lowerBound(arr, v) {
  let [l, r] = [-1, arr.length];
  while (l + 1 < r) {
    let mid = Math.floor((l + r) / 2);
    let target = arr[mid];
    if (v < target) {
      r = mid;
    } else {
      l = mid;
    }
  }
  return r;
}
