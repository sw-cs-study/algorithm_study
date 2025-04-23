const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

const [n, m] = input[0].split(" ").map(Number);
const edges = [...Array(n)].map(() => []); // 인접 점의 정보
const costs = [...Array(n)].map(() => Array(n).fill(Infinity)); // 간선의 비용
for (let i = 0; i < m; i++) {
  let [a, b, c] = input[1 + i].split(" ").map(Number);
  a--;
  b--;
  let pass = false;
  if (costs[a][b] < Infinity) { pass = true; } // 이미 들어가 있는 정보면 인접리스트 pass
  if (costs[a][b] <= c) { continue; } // 비용이 더 작을 때만 갱신
  edges[a].push(b);
  edges[b].push(a);
  if (pass) { continue; }
  costs[a][b] = c;
  costs[b][a] = c;
}

const d = Array(n).fill(Infinity);
d[0] = 0;
const prev = Array(n).fill(-1);
const visited = Array(n).fill(false);
for (let i of edges[0]) { // 슈퍼컴과 연결된 애들 정보 갱신
  d[i] = costs[0][i];
  prev[i] = 0;
}
visited[0] = true; // 슈퍼컴을 거쳐서 가는 시간 체크했으니깐 방문 처리
for (let t = 0; t < n - 1; t++) { // 마지막 연결까지 담아야해서 n-2까지 돌리는게 아니라 n-1까지 돌림
  // 방문 안한 컴퓨터 중에 시간이 가장 적은 컴터 선택
  let cur = 0;
  let min = Infinity;
  for (let i = 0; i < n; i++) {
    if (visited[i]) { continue; }
    if (d[i] < min) {
      min = d[i];
      cur = i;
    }
  }
  visited[cur] = true;
  // cur 컴퓨터를 거쳐서 아직 선택못받은 점들로 가는 시간 갱신
  for (let i = 0; i < n; i++) {
    if (visited[i]) { continue; }
    if (d[i] > d[cur] + costs[cur][i]) {
      d[i] = d[cur] + costs[cur][i];
      prev[i] = cur;
    }
  }
}
const answer = [];
for (let i = 0; i < prev.length; i++) {
  if (prev[i] === -1) { continue; }
  answer.push(`${i + 1} ${prev[i] + 1}`);
}
console.log(`${answer.length}\n${answer.join("\n")}`);

