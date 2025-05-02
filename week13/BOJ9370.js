/**
 * 미확인 도착지
 * 테케 최대 100개 => 3 * 10 ^ 8 / 100 => 3 * 10 ^ 6
 * 출발 지점 s에서 목적지 t(1~100)개로 가는 최단 경로 중에 g, h를 지나는지 확인해야함. 교차로 최대 2000개. 도로 최대 50000개
 * 고려할 점) 다익스트라는 써야할 것 같은데 그럼 기본적으로 O((V + E) * log(V))이므로 52000 * 11 = 572000 => 5개까진 가능?
 * 설계
 * 1. g, h를 반드시 지나가는지 판단해야함 => 하나의 다익스트라에서 경로 정보를 다 저장하고 탐색하지 않는 이상 특정할 수 없음(경로 50000개를 매번 체크하면 안됨)
 * 2. s에서 다익스트라로 각 점의 최소 거리르 구하고 g, h에서 서로를 지나지 않는 다익스트라를 구함
 * 3. s => t로 g,h 를 지나서 가는 거리가 s에서 출발한 다익스트라와 같으면 가능한 목적지임
 */
const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const taseCase = Number(input[0]);
let idx = 1;
for (let tc = 0; tc < taseCase; tc++) {
  const [n, m, t] = input[idx++].split(" ").map(Number);
  const [s, g, h] = input[idx++].split(" ").map(Number);
  const edges = [...Array(n + 1)].map(() => []);
  let ghDist;
  for (let i = 0; i < m; i++) {
    const [a, b, c] = input[idx++].split(" ").map(Number);
    edges[a].push([b, c]);
    edges[b].push([a, c]);
    if ((a === g && b === h) || (a === h && b === g)) {
      ghDist = c;
    }
  }
  const candidates = [];
  for (let i = 0; i < t; i++) {
    candidates.push(Number(input[idx++]));
  }

  const originalDist = [...Array(n + 1)].map(() => Infinity);
  const gDist = [...Array(n + 1)].map(() => Infinity);
  const hDist = [...Array(n + 1)].map(() => Infinity);

  dikjstra(s, edges, originalDist);
  dikjstra(g, edges, gDist);
  dikjstra(h, edges, hDist);

  let answer = [];
  for (let arrival of candidates) {
    const original = originalDist[arrival];
    if (original === Infinity) continue; // Infinity === infinity 인 경우가 발생할 수 있음
    const gToStart = gDist[s];
    const gToArrival = gDist[arrival];
    const hToStart = hDist[s];
    const hToArrival = hDist[arrival];
    if (
      original === gToStart + hToArrival + ghDist ||
      original === hToStart + gToArrival + ghDist
    ) {
      answer.push(arrival);
    }
  }
  console.log(answer.sort((a, b) => a - b).join(" "));
}

function minHeapPush(heap, v) {
  heap.push(v);
  let idx = heap.length - 1;
  let parent = Math.floor((idx - 1) / 2);
  while (idx > 0 && heap[parent][0] > heap[idx][0]) {
    [heap[parent], heap[idx]] = [heap[idx], heap[parent]];
    idx = parent;
    parent = Math.floor((idx - 1) / 2);
  }
}

function minHeapPop(heap) {
  if (heap.length === 0) return null;
  if (heap.length === 1) return heap.pop();
  const ret = [...heap[0]];
  heap[0] = heap.pop();
  let idx = 0;
  let child = idx * 2 + 1;
  if (child + 1 < heap.length && heap[child][0] > heap[child + 1][0]) child++;
  while (child < heap.length && heap[child][0] < heap[idx][0]) {
    [heap[child], heap[idx]] = [heap[idx], heap[child]];
    idx = child;
    child = idx * 2 + 1;
    if (child + 1 < heap.length && heap[child][0] > heap[child + 1][0]) child++;
  }
  return ret;
}

function dikjstra(start, edges, dist) {
  const heap = [];
  dist[start] = 0;
  minHeapPush(heap, [0, start]);
  while (heap.length > 0) {
    const [d, cur] = minHeapPop(heap);
    if (dist[cur] < d) continue;
    for (const [next, cost] of edges[cur]) {
      if (dist[next] > d + cost) {
        dist[next] = d + cost;
        minHeapPush(heap, [dist[next], next]);
      }
    }
  }
}
