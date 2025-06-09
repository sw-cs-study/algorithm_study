const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const testCase = Number(input[0]);
for (let t = 0; t < testCase; t++) {
  const k = Number(input[1 + 2 * t]);
  const numbers = input[1 + 2 * t + 1].split(" ").map(Number);
  // dp 배열은 구간의 길이, 시작 숫자들로 이루어짐 => n, n
  const dp = [...Array(k)].map(() => Array(k).fill(0));
  const prefixSum = Array(k).fill(0);
  prefixSum[0] = numbers[0];
  for (let i = 1; i < k; i++) {
    prefixSum[i] = prefixSum[i - 1] + numbers[i];
  }

  // i=1부터 순회하고, j는 n-i개 순회
  for (let i = 1; i < k; i++) {
    for (let j = 0; j < k - i; j++) {
      // 각 구간에 대해서 dp[i][j]: 임의의 d(0 <= d < i)에 대해서 [j, j + d] + (j + d + 1, j + i]로 총 합친 비용 중에 제일 작은 총 합친 비용
      // 합치는 값은 구간합으로 계산하고, 누적된 값은 dp배열로 계산
      let minValue = Infinity;
      // 합치는 비용
      let addValue = prefixSum[j + i] - (j === 0 ? 0 : prefixSum[j - 1]);
      for (let d = 0; d < i; d++) {
        // 이전에 누적된 값
        let accValue = dp[d][j] + dp[i - d - 1][j + d + 1];
        if (accValue < minValue) {
          minValue = accValue;
        }
      }
      dp[i][j] = minValue + addValue;
    }
  }
  console.log(dp[k - 1][0]);
}
