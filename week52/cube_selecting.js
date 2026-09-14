function solution(dice) {
  var answer = [];

  const n = dice.length;
  const half = n / 2;

  // 주사위 조합 만들기
  const combinations = [];

  const makeCombination = (start, result) => {
    if (result.length === half) {
      combinations.push([...result]);
      return;
    }

    for (let i = start; i < n; i++) {
      result.push(i);
      makeCombination(i + 1, result);
      result.pop();
    }
  };

  makeCombination(0, []);

  // 선택한 주사위들로 만들 수 있는 모든 합
  const getSums = (selected) => {
    const sums = [];

    const dfs = (depth, sum) => {
      if (depth === selected.length) {
        sums.push(sum);
        return;
      }

      const curDice = dice[selected[depth]];

      for (let i = 0; i < 6; i++) {
        dfs(depth + 1, sum + curDice[i]);
      }
    };

    dfs(0, 0);

    return sums;
  };

  // target보다 작은 값의 개수
  const lowerBound = (arr, target) => {
    let left = 0;
    let right = arr.length;

    while (left < right) {
      const mid = Math.floor((left + right) / 2);

      if (arr[mid] < target) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }

    return left;
  };

  let maxWin = -1;

  for (const selectedA of combinations) {
    const selectedSet = new Set(selectedA);
    const selectedB = [];

    for (let i = 0; i < n; i++) {
      if (!selectedSet.has(i)) {
        selectedB.push(i);
      }
    }

    const sumsA = getSums(selectedA);
    const sumsB = getSums(selectedB);

    sumsB.sort((a, b) => a - b);

    let win = 0;

    for (const scoreA of sumsA) {
      // scoreA보다 작은 B 점수 개수
      win += lowerBound(sumsB, scoreA);
    }

    if (win > maxWin) {
      maxWin = win;
      answer = selectedA.map((v) => v + 1);
    }
  }

  return answer;
}
