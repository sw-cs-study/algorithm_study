const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, m] = input[0].split(" ").map(Number);
const inEdge = [...Array(n + 1)].map(() => []);
const inDegree = Array(n + 1).fill(0);
for (let i = 0; i < m; i++) {
  const [a, b] = input[1 + i].split(" ").map(Number);
  inEdge[a].push(b);
  inDegree[b]++;
}

const priorityQueue = [];
for (let i = 1; i < n + 1; i++) {
  if (inDegree[i] === 0) {
    minHeapPush(priorityQueue, i);
  }
}
const answer = [];
for (let i = 0; i < n; i++) {
  let v = minHeapPop(priorityQueue);

  answer.push(v);
  for (let ni of inEdge[v]) {
    inDegree[ni]--;
    if (inDegree[ni] === 0) minHeapPush(priorityQueue, ni);
  }
}
console.log(answer.join(" "));

function minHeapPush(heap, v) {
  heap.push(v);
  let idx = heap.length - 1;
  let parent = Math.floor((idx - 1) / 2);
  while (idx > 0 && compare(heap[parent], heap[idx]) > 0) {
    [heap[parent], heap[idx]] = [heap[idx], heap[parent]];
    idx = parent;
    parent = Math.floor((idx - 1) / 2);
  }
}

function minHeapPop(heap) {
  if (heap.length === 0) return null;
  if (heap.length === 1) return heap.pop();
  const ret = heap[0];
  heap[0] = heap.pop();
  let idx = 0;
  let child = idx * 2 + 1;
  if (child + 1 < heap.length && compare(heap[child], heap[child + 1]) > 0)
    child++;
  while (child < heap.length && compare(heap[child], heap[idx]) < 0) {
    [heap[child], heap[idx]] = [heap[idx], heap[child]];
    idx = child;
    child = idx * 2 + 1;
    if (child + 1 < heap.length && compare(heap[child], heap[child + 1]) > 0)
      child++;
  }
  return ret;
}

function compare(a, b) {
  if (a > b) return 1;
  else if (a === b) return 0;
  return -1;
}
