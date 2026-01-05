const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, m, d, e] = input[0].split(" ").map(Number);
const height = [0, ...input[1].split(" ").map(Number)];

const edges = [...Array(n + 1)].map(() => new Object()); // {target: cost}
for (let i = 0; i < m; i++) {
  let [a, b, w] = input[2 + i].split(" ").map(Number);

  if (height[a] === height[b]) continue;
  if (height[b] < height[a]) {
    [a, b] = [b, a];
  }
  if (edges[a][b] === undefined) {
    edges[a][b] = w;
  } else {
    if (w < edges[a][b]) {
      edges[a][b] = w;
    }
  }
}

// 1 -> target
const dikjstra1 = getDikjstra(1);
// n -> target
const dikjstra2 = getDikjstra(n);

let answer = -Infinity;
for (let i = 2; i < n; i++) {
  let cost = dikjstra1[i] + dikjstra2[i];
  let v = e * height[i] - cost * d;
  if (v > answer) {
    answer = v;
  }
}
console.log(answer !== -Infinity ? answer : "Impossible");

function getDikjstra(start) {
  const pq = [[0, start]];
  const dikjstra = Array(n + 1).fill(Infinity);
  dikjstra[start] = 0;

  while (pq.length > 0) {
    let [cost, cur] = heapPop(pq);
    if (cost > dikjstra[cur]) {
      continue;
    }
    for (let nextNode of Object.keys(edges[cur]).map(Number)) {
      let sum = cost + edges[cur][nextNode];
      if (dikjstra[nextNode] > sum) {
        dikjstra[nextNode] = sum;
        heapPush(pq, [sum, nextNode]);
      }
    }
  }

  return dikjstra;
}

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
