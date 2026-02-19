// 8! = 40320

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const n = Number(input[0]);
const arr = input[1].split(" ").map(Number);
const m = Number(input[2]);
const commands = [];
for (let i = 0; i < m; i++) {
  let [x, y, c] = input[3 + i].split(" ").map(Number);
  commands.push({ el1: x - 1, el2: y - 1, cost: c });
}

// 주어진 배열로 노드마다 도달하는데 필요한 비용을 적은 dijkstra 배열 생성: 최대 8자리에 10이하의 값이라 최대 16자리의 수가 나옴나옴
// 그래서 map<string, number>으로 만들기
const dijkstra = {};
const allNumbers = getNumbers(arr);
allNumbers.forEach((v, i) => {
  dijkstra[getKey(v)] = Infinity;
});

// 다익스트라 진행 후 조건에 맞는 숫자 배열 중 가장 비용이 적은 수 출력...
// 다익스트라 진행
let key = getKey(arr);
dijkstra[key] = 0; // 초기 상태
const pq = [[0, key]];

while (pq.length > 0) {
  let [cost, key] = heapPop(pq);
  if (cost > dijkstra[key]) continue;

  let ori = getArr(key);
  commands.forEach((c, i) => {
    let nextCost = cost + c.cost;
    let temp = [...ori];
    [temp[c.el1], temp[c.el2]] = [temp[c.el2], temp[c.el1]];
    let nextKey = getKey(temp);
    if (nextCost < dijkstra[nextKey]) {
      dijkstra[nextKey] = nextCost;
      heapPush(pq, [nextCost, nextKey]);
    }
  });
}

// 조건에 맞는(비내림차순) 답의 비용 중 제일 적은 비용 출력
const candi = [];
allNumbers.forEach((arr) => {
  let notDesc = true;
  let prev = -1;
  arr.forEach((v) => {
    if (v < prev) {
      notDesc = false;
    }
    prev = v;
  });
  if (notDesc) {
    candi.push(getKey(arr));
  }
});
let answer = Infinity;
candi.forEach((key) => {
  if (dijkstra[key] < answer) answer = dijkstra[key];
});
console.log(answer !== Infinity ? answer : -1);

// pq
function heapPush(arr, v) {
  arr.push(v);
  let idx = arr.length - 1;
  let parentIdx = Math.floor((idx - 1) / 2);
  while (idx > 0 && compare(arr[idx], arr[parentIdx])) {
    [arr[parentIdx], arr[idx]] = [arr[idx], arr[parentIdx]];
    idx = parentIdx;
    parentIdx = Math.floor((idx - 1) / 2);
  }
}

function heapPop(arr) {
  if (arr.length === 0) return [null, null];
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

function compare(arr1, arr2) {
  if (arr1[0] < arr2[0]) return true;
  return false;
}

// 조합으로 가능한 숫자 배열 만들기
function getNumbers(numbers) {
  const k = numbers.length;
  const result = [];
  if (k === 0) {
    return [[]];
  }

  numbers.forEach((v1, i1, arr1) => {
    const nextNumbers = [...arr1.slice(0, i1), ...arr1.slice(i1 + 1)];
    const nextCombi = getNumbers(nextNumbers, k - 1);

    nextCombi.forEach((arr2) => {
      result.push([v1, ...arr2]);
    });
  });

  return result;
}

// 현재 조합 문자열로 변환
function getKey(arr) {
  return arr.join("");
}

function getArr(key) {
  let temp = key.split("10");
  let ret = [];
  temp.forEach((v, i, arr) => {
    ret.push(...v.split("").map(Number));
    if (i < arr.length - 1) ret.push(10);
  });

  return ret;
}
