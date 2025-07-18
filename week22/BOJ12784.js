const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const tc = Number(input[0]);
let idx = 1;
let answer = "";
for (let t = 0; t < tc; t++) {
  const [n, m] = input[idx++].split(" ").map(Number);
  const infos = [...Array(n + 1)].map(() => []);
  for (let i = 0; i < m; i++) {
    let [a, b, c] = input[idx++].split(" ").map(Number);
    infos[a].push([b, c]);
    infos[b].push([a, c]);
  }
  const visited = Array(n + 1).fill(false);
  const recur = (node) => {
    let ret = Infinity;
    let totalCost = 0;
    for (let [nextNode, cost] of infos[node]) {
      if (visited[nextNode]) continue;
      visited[nextNode] = true;
      totalCost += Math.min(cost, recur(nextNode));
    }
    if (totalCost > 0) {
      ret = totalCost;
    }

    return ret;
  };

  visited[1] = true;
  let cost = recur(1);
  answer += `${cost === Infinity ? 0 : cost}\n`;
}
console.log(answer);
