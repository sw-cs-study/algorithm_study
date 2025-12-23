const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, k] = input[0].split(" ").map(Number);
const queueForLine = []; // [delay time, counter]
for (let i = 1; i < k + 1; i++) {
  heapPush(queueForLine, [0, i], compareForIn);
}
const pq = []; // [delay, counter, customerNumber];

for (let i = 0; i < n; i++) {
  let [id, w] = input[1 + i].split(" ").map(Number);
  let [delay, counter] = heapPop(queueForLine, compareForIn); // 제일 대기시간이 작은 계산대 들고오기
  heapPush(queueForLine, [delay + w, counter], compareForIn);
  heapPush(pq, [delay + w, counter, id], compareForOut);
}

const done = [];
while (pq.length > 0) {
  let [delay, counter, id] = heapPop(pq, compareForOut);
  done.push(id);
}
let answer = 0n;
for (let i = 0; i < done.length; i++) {
  answer += BigInt((i + 1) * done[i]);
}
console.log(answer.toString());

function heapPush(arr, v, compare) {
  arr.push(v);
  let idx = arr.length - 1;
  let parentIdx = Math.floor((idx - 1) / 2);
  while (idx > 0 && compare(arr[idx], arr[parentIdx])) {
    [arr[parentIdx], arr[idx]] = [arr[idx], arr[parentIdx]];
    idx = parentIdx;
    parentIdx = Math.floor((idx - 1) / 2);
  }
}

function heapPop(arr, compare) {
  if (arr.length === 0) return null;
  if (arr.length === 1) return arr.pop();
  let ret = [...arr[0]];
  arr[0] = arr.pop();
  let idx = 0;
  let childIdx = 2 * idx + 1;
  if (childIdx + 1 < arr.length && compare(arr[childIdx + 1], arr[childIdx])) {
    childIdx++;
  }
  while (childIdx < arr.length && compare(arr[childIdx], arr[idx])) {
    [arr[childIdx], arr[idx]] = [arr[idx], arr[childIdx]];
    idx = childIdx;
    childIdx = 2 * idx + 1;
    if (
      childIdx + 1 < arr.length &&
      compare(arr[childIdx + 1], arr[childIdx])
    ) {
      childIdx++;
    }
  }

  return ret;
}

function compareForIn(arr1, arr2) {
  if (arr1[0] < arr2[0]) return true;

  if (arr1[0] === arr2[0] && arr1[1] < arr2[1]) return true;
  return false;
}

function compareForOut(arr1, arr2) {
  if (arr1[0] < arr2[0]) return true;

  if (arr1[0] === arr2[0] && arr1[1] > arr2[1]) return true;
  return false;
}
