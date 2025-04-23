const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "sample.txt")
  .toString()
  .trim()
  .split("\n");

const A = input[0].trim();
const B = input[1].trim();
solve(A, B);


function solve(A, B) {
  const dp = [...Array(B.length + 1)].map(() => Array(A.length + 1).fill(0)); // 최소 거리(만들수 있는 연산수)를 저장

  for (let i = 0; i < B.length + 1; i++) {
    for (let j = 0; j < A.length + 1; j++) {
      if (i === 0) {
        dp[i][j] = j;
        continue;
      }

      if (j === 0) {
        dp[i][j] = i;
        continue;
      }

      if (B[i - 1] === A[j - 1]) {
        dp[i][j] = dp[i - 1][j - 1];
      } else {
        // 현재B[i] 추가(i-1, j), A[j]를 B[i]로 교체(i-1, j-1), 현재A[j] 삭제해서 B[i]랑 맞추기
        dp[i][j] = 1 + Math.min(dp[i - 1][j], dp[i - 1][j - 1], dp[i][j - 1]);
      }
    }
  }
  console.log(dp[B.length][A.length]);
}
