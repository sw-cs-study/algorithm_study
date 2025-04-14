const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const t = Number(input[0]);
let plus = 1;
for (let tc = 0; tc < t; tc++) {
  let [k, m, p] = input[plus].split(" ").map(Number);
  const edges = [...Array(m + 1)].map(() => []); // i번째 노드로 들어오는 점
  plus++;
  for (let i = 0; i < p; i++) {
    let [a, b] = input[plus].split(" ").map(Number);
    edges[b].push(a);
    plus++;
  }
  const memo = Array(m + 1).fill(0);
  console.log(k, recur(m, memo, edges));
}

function recur(cur, memo, edges) {
  if (edges[cur].length === 0) {
    // root node
    return 1;
  }
  if (memo[cur] !== 0) {
    return memo[cur];
  }
  let level = 0;
  let cnt = 0;
  for (let node of edges[cur]) {
    let result = recur(node, memo, edges);
    if (result > level) {
      level = result;
      cnt = result;
    } else if (result === level) {
      cnt = result + 1;
    }
  }
  memo[cur] = cnt;
  return cnt;
}
