const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, m] = input[0].split(" ").map(Number);
const edges = [...Array(n + 1)].map(() => []);
const d = [...Array(n + 1)].map(() => Array(n + 1).fill(Infinity));
for (let i = 0; i < m; i++) {
  let [a, b] = input[1 + i].split(" ").map(Number);
  edges[a].push(b);
  edges[b].push(a);
  d[a][b] = 1;
  d[b][a] = 1;
}
for (let i = 1; i < n + 1; i++) {
  d[i][i] = 0;
}

for (let k = 1; k < n + 1; k++) {
  for (let a = 1; a < n + 1; a++) {
    for (let b = 1; b < n + 1; b++) {
      d[a][b] = Math.min(d[a][b], d[a][k] + d[k][b]);
      d[b][a] = d[a][b];
    }
  }
}
let time = Infinity;
let pair = [0, 0];
for (let s = 1; s < n; s++) {
  for (let e = s + 1; e < n + 1; e++) {
    let v = 0;
    for (let i = 1; i < n + 1; i++) {
      v += 2 * Math.min(d[i][s], d[i][e]);
    }
    if (v < time) {
      time = v;
      pair[0] = s;
      pair[1] = e;
    }
  }
}
console.log(...pair, time);

function minHeapPush(arr, v) {
  arr.push(v);
  let idx = arr.length - 1;
  let parent = Math.floor((idx - 1) / 2);
  while (parent >= 0 && arr[parent][0] > arr[idx][0]) {
    [arr[parent], arr[idx]] = [arr[idx], arr[parent]];
    idx = parent;
    parent = Math.floor((idx - 1) / 2);
  }
}

function minHeapPop(arr) {
  let ret = [...arr[0]];
  arr[0] = arr.pop();
  let idx = 0;
  let child = idx * 2 + 1;
  if (child + 1 < arr.length && arr[child][0] > arr[child + 1][0]) {
    child += 1;
  }
  while (child < arr.length && arr[idx][0] > arr[child][0]) {
    [arr[idx], arr[child]] = [arr[child], arr[idx]];
    idx = 0;
    child = idx * 2 + 1;
    if (child + 1 < arr.length && arr[child][0] > arr[child + 1][0]) {
      child += 1;
    }
  }
  return ret;
}
