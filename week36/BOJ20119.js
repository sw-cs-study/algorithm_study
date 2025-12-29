const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, m] = input[0].split(" ").map(Number);
const indegreeArray = Array(n + 1).fill(Infinity);
const targets = Array(n + 1).fill(0);
const edges = [...Array(n + 1)].map(() => []);

for (let i = 0; i < m; i++) {
  let line = input[1 + i].split(" ").map(Number);
  let k = line[0];
  let route = line.slice(1, k + 1);
  let target = line[k + 1];

  indegreeArray[i] = k;
  targets[i] = target;
  for (let r of route) {
    edges[r].push(i);
  }
}

const queue = [];
const complete = {};
const L = Number(input[1 + m]);
const had = input[1 + m + 1].split(" ").map(Number);
for (let p of had) {
  queue.push(p);
  complete[p] = true;
}

while (queue.length > 0) {
  let p = queue.shift();
  for (let targetId of edges[p]) {
    indegreeArray[targetId]--;
    if (indegreeArray[targetId] === 0 && !complete[targets[targetId]]) {
      queue.push(targets[targetId]);
      complete[targets[targetId]] = true;
    }
  }
}

let answer = [];
for (let p of Object.keys(complete)) {
  answer.push(p);
}
answer.sort((a, b) => a - b);
console.log(answer.length);
console.log(answer.join(" "));
