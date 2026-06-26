/**
 * int 가능
 * 1에서 n까지 도달하는데 k개의 가격은 무시하고 남은 것 중 제일 큰 가격만 지불. 이때 지불 가격이 최소일 때의 값.
 *
 * 고려할 점
 * 컴퓨터의 개수보다 선의 갯수가 많으므로 더 많이 들고 있음.
 *
 * 탐색 횟수가 너무 많은 경우 이 탐색 횟수를 제한하기 위해 이분 탐색을 사용함.
 * 문제의 조건에 맞게 인터넷을 연결할 때 지불할 금액을 먼저 정한다. 그리고 이를 검증하고 통과된다면 더 낮은 금액도 검증하고, 통과하지 않는다면 더 높은 금액을 잡고 다시 검증한다.
 * 여기서 지불할 금액을 입력으로 받는 각 금액에서 이분 탐색으로 정한다.
 *
 * 검증 방법
 *
 */

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, p, k] = input[0].split(" ").map(Number);
const infos = [...Array(n + 1)].map(() => []);
const candidates = [];
for (let i = 0; i < p; i++) {
  let [a, b, v] = input[1 + i].split(" ").map(Number);
  candidates.push(v);
  infos[a].push([b, v]);
  infos[b].push([a, v]);
}

candidates.sort((a, b) => a - b);
let [l, r] = [0, p]; /// TTTFF => 가장 오른쪽 T

while (l + 1 < r) {
  let mid = Math.floor((l + r) / 2);
  // 다익스트라로 돌리기
  let target = Infinity;
}

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
