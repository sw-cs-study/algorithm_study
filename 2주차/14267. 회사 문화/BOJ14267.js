const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

let [n, m] = input[0].split(" ").map(Number);
const bossTable = [0, ...input[1].split(" ").map(Number)];
const answer = [];
const juniorTable = [];
for (let a of bossTable) {
  answer.push(0);
  juniorTable.push([]);
}
for (let i = 2; i < n + 1; i++) {
  juniorTable[bossTable[i]].push(i);
}

for (let i = 0; i < m; i++) {
  let [p, w] = input[2 + i].split(" ").map(Number);
  answer[p] += w;
}

for (let i = 2; i < n + 1; i++) {
  for (let p of juniorTable[i]) {
    answer[p] += answer[i];
  }
}

console.log(answer.slice(1).join(" "));
