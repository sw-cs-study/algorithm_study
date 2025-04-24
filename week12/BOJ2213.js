// 트리의 독립집합

/**
 * 1. 간선 정보를 저장(edges)하고 재귀 함수로 루트를 1로 잡아서 탑다운 dp를 구현
 * 2. memo[i][0] = i를 포함하지 않고 자식들로 만들 수 있는 최대 독립 집합의 크기
 * 3. memo[i][1] = i를 포함하고 자식들로 만들 수 있는 최대 독립 집합의 크기
 * 4. memo[i][0] = sum(max(memo[자식노드][1], memo[자식노드][0])) => 자식 노드의 가중치가 더 큰 것을 선택
 * 5. memo[i][1] = sum(memo[자식노드][0]) + 현재 정점 가중치
 * 근데 속하는 집합을 오름차순으로 출력해야함... memo에 저장할 때, 정점 정보까지 넣기
 * 최대 독립 집합이 하나 이상인 경우 아무거나 출력하면 되므로 같은 경우는 자유롭게 함
 * O(n)임.
 */

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const n = Number(input[0]);
const values = [0, ...input[1].split(" ").map(Number)];
const edges = [...Array(n + 1)].map(() => []);
const visited = Array(n + 1).fill(false);
const memo = [...Array(n + 1)].map(() => [...Array(2)].map(() => [0, []])); // memo[i][0] = i를 포함하지 않고 자식들로 만들 수 있는 최대 독립 집합의 크기, memo[i][1] = i를 포함하고 자식들로 만들 수 있는 최대 독립 집합의 크기
for (let i = 0; i < n - 1; i++) {
  const [a, b] = input[2 + i].split(" ").map(Number);
  edges[a].push(b);
  edges[b].push(a);
}
recur(1);
let ans;
if (memo[1][0][0] > memo[1][1][0]) {
  memo[1][0][1].sort((a, b) => a - b);
  ans = `${memo[1][0][0]}\n`;
  ans += memo[1][0][1].join(" ");
} else {
  memo[1][1][1].sort((a, b) => a - b);
  ans = `${memo[1][1][0]}\n`;
  ans += memo[1][1][1].join(" ");
}
console.log(ans);

function recur(node) {
  // memo
  if (memo[node][0][0] !== 0 || memo[node][1][0] !== 0) return;

  memo[node][1] = [values[node], [node]]; // 현재 정점 포함에 정보 넣기
  visited[node] = true;
  for (const nextNode of edges[node]) {
    if (visited[nextNode]) continue;
    recur(nextNode);
    // 현재 정점 미포함
    if (memo[nextNode][0][0] > memo[nextNode][1][0]) {
      memo[node][0][0] += memo[nextNode][0][0];
      memo[node][0][1] = [...memo[node][0][1], ...memo[nextNode][0][1]];
    } else {
      memo[node][0][0] += memo[nextNode][1][0];
      memo[node][0][1] = [...memo[node][0][1], ...memo[nextNode][1][1]];
    }
    // 현재 정점 포함
    memo[node][1][0] += memo[nextNode][0][0];
    memo[node][1][1] = [...memo[node][1][1], ...memo[nextNode][0][1]];
  }
  return;
}
