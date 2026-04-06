const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, s, e, m] = input[0].split(" ").map(Number);
const edges = [...Array(n)].map(() => []);

for (let i = 0; i < m; i++) {
  let [a, b, c] = input[1 + i].split(" ").map(Number);
  edges[a].push([b, c]);
}

const pays = input[1 + m].split(" ").map(Number);

const state = Array(n).fill(-Infinity);
state[s] = pays[s];

for (let i = 0; i < n - 1; i++) {
  for (let node = 0; node < n; node++) {
    for (let [nextNode, cost] of edges[node]) {
      let result = state[node] - cost + pays[nextNode];
      if (state[nextNode] < result) {
        state[nextNode] = result;
      }
    }
  }
}

let inCycle = new Set();
loop1: for (let node = 0; node < n; node++) {
  for (let [nextNode, cost] of edges[node]) {
    let result = state[node] - cost + pays[nextNode];
    if (state[nextNode] < result) {
      inCycle.add(nextNode);
      inCycle.add(node);
    }
  }
}
let positiveCycle = false;
let queue = [...inCycle];
let visited = Array(n).fill(false);
loop1: while (queue.length > 0) {
  let cur = queue.shift();
  if (cur === e) {
    positiveCycle = true;
    break loop1;
  }
  if (visited[cur]) continue;
  visited[cur] = true;

  for (let [nextNode, _] of edges[cur]) {
    if (visited[nextNode]) continue;
    if (nextNode === e) {
      positiveCycle = true;
      break loop1;
    }
    queue.push(nextNode);
  }
}

if (state[e] === -Infinity) {
  console.log("gg");
} else if (positiveCycle) {
  console.log("Gee");
} else {
  console.log(state[e]);
}
