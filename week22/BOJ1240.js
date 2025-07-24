const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, m] = input[0].split(" ").map(Number);
const edges = [...Array(n + 1)].map(() => []);
for (let i = 1; i < n; i++) {
  let [a, b, d] = input[i].split(" ").map(Number);
  edges[a].push([b, d]);
  edges[b].push([a, d]);
}

let answer = "";
let visited;
for (let i = n; i < n + m; i++) {
  let [a, b] = input[i].split(" ").map(Number);
  visited = Array(n + 1).fill(false);
  answer += `${recur(a, b)}\n`;
}
console.log(answer);

function recur(cur, e) {
  if (cur === e) return 0;
  visited[cur] = true;

  let ret = -Infinity;
  for (let [nextNode, dist] of edges[cur]) {
    if (visited[nextNode]) continue;
    let result = recur(nextNode, e);
    if (result >= 0) {
      ret = result + dist;
      break;
    }
  }
  visited[cur] = false;

  return ret;
}
