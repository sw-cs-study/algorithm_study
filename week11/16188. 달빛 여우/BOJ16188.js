const input = require("fs")
  .readFileSync(
    process.platform === "linux" ? "/dev/stdin" : "../../sample.txt"
  )
  .toString()
  .trim()
  .split("\n");

const [n, m] = input[0].split(" ").map(Number);
const edges = [...Array(n + 1)].map(() => []); // i번째 노드에 연결된 점, 거리 정보
for (let i = 0; i < m; i++) {
  let [a, b, c] = input[1 + i].split(" ").map(Number);
  edges[a].push([b, c]);
  edges[b].push([a, c]);
}

const foxDist = Array(n + 1).fill(Infinity);
const wolfDist = [...Array(n + 1)].map(() => Array(2).fill(Infinity));
foxDist[1] = 0;
wolfDist[1][0] = 0;
const pq = [];
// fox
pq.push([0, 1]);
while (pq.length > 0) {
  let [dist, cur] = heapPop();
  if (dist > foxDist[cur]) continue;
  for (let [nextNode, cost] of edges[cur]) {
    if (foxDist[nextNode] > dist + cost) {
      foxDist[nextNode] = dist + cost;
      heapPush([foxDist[nextNode], nextNode]);
    }
  }
}
// wolf
pq.push([0, 1, 0]);
while (pq.length > 0) {
  let [dist, cur, passedCounts] = heapPop();
  let state = passedCounts % 2;
  if (dist > wolfDist[cur][state]) continue;
  for (let [nextNode, cost] of edges[cur]) {
    if (state) cost *= 2;
    else cost /= 2;
    if (wolfDist[nextNode][(state + 1) % 2] > dist + cost) {
      wolfDist[nextNode][(state + 1) % 2] = dist + cost;
      heapPush([
        wolfDist[nextNode][(state + 1) % 2],
        nextNode,
        passedCounts + 1,
      ]);
    }
  }
}
let count = 0;
for (let i = 2; i < n + 1; i++) {
  if (foxDist[i] < wolfDist[i][0] && foxDist[i] < wolfDist[i][1]) count++;
}
console.log(count);

function heapPush(arr) {
  pq.push(arr);
  let curIdx = pq.length - 1;
  let parentIdx = Math.floor((curIdx - 1) / 2);
  while (curIdx > 0 && pq[curIdx][0] < pq[parentIdx][0]) {
    [pq[curIdx], pq[parentIdx]] = [pq[parentIdx], pq[curIdx]];
    curIdx = parentIdx;
    parentIdx = Math.floor((curIdx - 1) / 2);
  }
}

function heapPop() {
  if (pq.length === 0) return null;
  if (pq.length === 1) return pq.pop();
  let ret = [...pq[0]];
  pq[0] = pq.pop();
  let curIdx = 0;
  let childIdx = 2 * curIdx + 1;
  if (childIdx + 1 < pq.length && pq[childIdx + 1][0] < pq[childIdx][0])
    childIdx++;
  while (childIdx < pq.length && pq[childIdx][0] < pq[curIdx][0]) {
    [pq[curIdx], pq[childIdx]] = [pq[childIdx], pq[curIdx]];
    curIdx = childIdx;
    childIdx = 2 * curIdx + 1;
    if (childIdx + 1 < pq.length && pq[childIdx + 1][0] < pq[childIdx][0])
      childIdx++;
  }
  return ret;
}
