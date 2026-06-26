const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const MAX = 1000000001;
const [N, M, A, B, C] = input[0].split(" ").map(Number);
const routes = [...Array(N + 1)].map(() => []);
for (let i = 0; i < M; i++) {
  let [s, e, cost] = input[1 + i].split(" ").map(Number);
  routes[s].push([cost, e]);
  routes[e].push([cost, s]);
}

let [l, r] = [0, MAX];
while (l + 1 < r) {
  let mid = Math.floor((l + r) / 2);
  if (checkRoute(mid)) {
    // 통과하면 골목의 수치심의 최댓값이 mid이하인 경로가 존재함
    r = mid;
  } else {
    l = mid;
  }
}
console.log(r === MAX ? -1 : r);

/**
 * A에서 B로 C이하의 수치심으로 가는 경로에서 최대 수치심이 maxCost이하라면 true 반환
 * @param {*} maxCost
 */
function checkRoute(maxCost) {
  const dijkstra = Array(N + 1).fill(Infinity);
  dijkstra[A] = 0;
  const pq = [[0, A]];
  while (pq.length > 0) {
    let [dist, node] = minHeapPop(pq);
    if (dist > dijkstra[node]) continue;
    for (let [nextDist, nextNode] of routes[node]) {
      const wholeCost = dist + nextDist;
      if (wholeCost >= dijkstra[nextNode]) continue;

      if (nextDist > maxCost) continue;

      dijkstra[nextNode] = wholeCost;
      minHeapPush(pq, [wholeCost, nextNode]);
    }
  }

  if (dijkstra[B] === Infinity) return false;
  return true;
}

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
  if (heap.length === 0) return [null, null];
  if (heap.length === 1) return heap.pop();
  const ret = [...heap[0]];
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
  if (a[0] > b[0]) return 1;
  else if (a[0] === b[0]) return 0;
  return -1;
}
