const input = require("fs")
  .readFileSync(
    process.platform === "linux" ? "/dev/stdin" : "../../sample.txt"
  )
  .toString()
  .trim()
  .split("\n");

const [n, m] = input[0].split(" ").map(Number);
const edges = [...Array(n + 1)].map(() => []);
for (let i = 0; i < m; i++) {
  let [a, b, c] = input[1 + i].split(" ").map(Number);
  edges[a].push([b, c]);
  edges[b].push([a, c]);
}
const dijkstra = Array(n + 1).fill(Infinity);
const pq = [[0, 2]];
dijkstra[2] = 0;
const memo = Array(n + 1).fill(0n);
memo[2] = 1n;
while (pq.length > 0) {
  let [dist, node] = heapPop();

  if (dist > dijkstra[node]) continue;

  for (let [nn, d] of edges[node]) {
    if (dist + d < dijkstra[nn]) {
      dijkstra[nn] = dist + d;
      heapPush([dist + d, nn]);
    }

    if (dist > dijkstra[nn]) {
      memo[node] += memo[nn];
    }
  }
}

console.log(String(memo[1]));

function heapPush(v) {
  pq.push(v);
  let idx = pq.length - 1;
  let parent = Math.floor((idx - 1) / 2);
  while (idx > 0 && pq[idx][0] < pq[parent][0]) {
    [pq[parent], pq[idx]] = [pq[idx], pq[parent]];
    idx = parent;
    parent = Math.floor((idx - 1) / 2);
  }
}

function heapPop() {
  if (pq.length === 0) {
    return null;
  }
  if (pq.length === 1) {
    return pq.pop();
  }
  let ret = [...pq[0]];
  pq[0] = pq.pop();
  let idx = 0;
  let child = idx * 2 + 1;
  if (child + 1 < pq.length && pq[child][0] > pq[child + 1][0]) {
    child++;
  }
  while (child < pq.length && pq[idx][0] > pq[child][0]) {
    [pq[child], pq[idx]] = [pq[idx], pq[child]];
    idx = child;
    child = idx * 2 + 1;
    if (child + 1 < pq.length && pq[child][0] > pq[child + 1][0]) {
      child++;
    }
  }
  return ret;
}
