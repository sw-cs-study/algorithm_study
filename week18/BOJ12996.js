/**
 * acka
 * memo 해서 연산수를 줄여야함
 * 50C25만해도 Number 넘음 => BigInt
 * n개와 각 부를 수 있는 곡수를 a, b, c라고 할때, n개를 먼저 채우는게 (a) (b) (c) (a, b) (b, c) (a, c) (a, b, c) 인 경우를 나눠서 구해야함(중복존재..)
 * 조합으로 접근했는데 생각보다 까다로움 그래서 탑다운 디피로
 */

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, ...arr] = input[0].split(" ").map(Number);
const MOD = 1000000007;

// [i][a][b][c] 일 때, i개의 곡을 부를 때 a곡, b곡, c곡을 부를 세명이서 앨범을 만드는 경우의 수
const memo = [...Array(n + 1)].map(() =>
  [...Array(arr[0] + 1)].map(() =>
    [...Array(arr[1] + 1)].map(() => Array(arr[2] + 1).fill(-1))
  )
);
console.log(solve(n, ...arr));

// 남은 곡수, a, b, c
function solve(rest, a, b, c) {
  if (rest === 0) {
    if (a === 0 && b === 0 && c === 0) {
      return 1;
    }
    return 0;
  }
  if (a < 0 || b < 0 || c < 0) return 0;

  if (memo[rest][a][b][c] > -1) return memo[rest][a][b][c];

  let ret = 0;
  ret += solve(rest - 1, a - 1, b, c);
  ret %= MOD;
  ret += solve(rest - 1, a, b - 1, c);
  ret %= MOD;
  ret += solve(rest - 1, a, b, c - 1);
  ret %= MOD;
  ret += solve(rest - 1, a - 1, b - 1, c);
  ret %= MOD;
  ret += solve(rest - 1, a, b - 1, c - 1);
  ret %= MOD;
  ret += solve(rest - 1, a - 1, b, c - 1);
  ret %= MOD;
  ret += solve(rest - 1, a - 1, b - 1, c - 1);
  ret %= MOD;

  memo[rest][a][b][c] = ret;
  return ret;
}
