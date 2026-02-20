const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const n = Number(input[0]);
const edges = [...Array(n + 1)].map(() => []); // s: [e, 간선순서번호, 주어진정보와의 정뱡향 여부]
for (let i = 0; i < n - 1; i++) {
  let [s, e] = input[1 + i].split(" ").map(Number);
  edges[s].push([e, i, true]);
  edges[e].push([s, i, false]);
}
// k번부터 자식 노드 정보를 저장
const makeTree = (cur, visited, tree) => {
  let ret = 0;

  edges[cur].forEach((v, i) => {
    let [e, edgeIndex, bool] = v;
    if (visited[e]) return;
    if (!bool) ret += 1;
    tree[cur].add(e);
    visited[e] = true;
    ret += makeTree(e, visited, tree);
  });

  return ret;
};

// 1번 노드를 루트로 트리 정보 생성
let tree = [...Array(n + 1)].map(() => new Set()); // i번 노드의 자식 노드 정보
let visited = Array(n + 1).fill(false);
visited[1] = true;
// 1번를 루트로 할 때, 바꿔야하는 간선 갯수 구하기
let count = makeTree(1, visited, tree);
// 1번을 루트로 하는 트리를 내려가면서 각 노드를 루트로 하는 트리를 만들 때, 바꿔야하는 간선의 갯수가 최소인 노드 구하기
let root = 1;
let value = count;
let stack = [[1, count]];
while (stack.length > 0) {
  let [cur, count] = stack.pop();

  edges[cur].forEach((info) => {
    let [e, _, bool] = info;
    if (!tree[cur].has(e)) return;

    let eRootCount = count;
    if (!bool) {
      eRootCount = count - 1;
      if (eRootCount < value) {
        root = e;
        value = eRootCount;
      }
    } else {
      eRootCount = count + 1;
    }
    stack.push([e, eRootCount]);
  });
}
// 찾은 노드를 루트로 하는 트리를 내려가면서 바꿔야하는 간선 정보 저장
const edgeChangeArr = Array(n - 1).fill("0");
visited = Array(n + 1).fill(false);
visited[root] = true;
stack = [root];
while (stack.length > 0) {
  let cur = stack.pop();

  edges[cur].forEach((info) => {
    let [e, edgeIndex, bool] = info;
    if (visited[e]) return;
    visited[e] = true;
    if (!bool) edgeChangeArr[edgeIndex] = "1";
    stack.push(e);
  });
}

console.log(edgeChangeArr.join(""));
