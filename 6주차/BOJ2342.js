const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "sample.txt")
  .toString()
  .trim()
  .split("\n");

const commands = [0, ...input[0].split(" ").map(Number)];
const dp = [...Array(commands.length - 1)].map(() =>
  [...Array(5)].map(() => Array(5).fill(Infinity))
);
console.log(recur());

function recur(idx = 0, l = 0, r = 0) {
  if (idx === commands.length - 2) return 0;

  if (dp[idx][l][r] !== Infinity) return dp[idx][l][r];

  let lMove = move(l, commands[idx + 1]) + recur(idx + 1, commands[idx + 1], r);
  let rMove = move(r, commands[idx + 1]) + recur(idx + 1, l, commands[idx + 1]);
  dp[idx][l][r] = Math.min(lMove, rMove);

  return dp[idx][l][r];
}

function move(a, b) {
  if (a === b) return 1;
  if (a === 0 && b > 0) return 2;
  if (Math.abs(a - b) % 2 === 1) return 3;
  return 4;
}
